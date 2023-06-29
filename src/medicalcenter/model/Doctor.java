package medicalcenter.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import medicalcenter.interfaces.CanRemitPatientToOtherCenter;
import medicalcenter.interfaces.CanRemitPatientToSpecialist;
import medicalcenter.util.Arrays;

public abstract class Doctor<S extends MedicalService<? extends Doctor<? super S>>>
		extends MedicalEmployee
		implements CanRemitPatientToSpecialist, CanRemitPatientToOtherCenter
{
	private S medicalService;

	private final Set<Class<? extends MedicalService>> specialities = new LinkedHashSet<>();


	protected Doctor(String nid, String name) {
		super(nid, name);
	}

	protected Doctor(String nid, String name, String phoneNumber) {
		super(nid, name, phoneNumber);
	}


	@Override
	protected String defineAttrs() {
		Class<?>[] specialities = this.specialities.toArray(Arrays.genericArray(Class[]::new)); //NOSONAR
		return String.format(
				"%s, specialities=%s",
				super.defineAttrs(),
				Arrays.toString(specialities, MedicalService.MEDICAL_SERVICE_NAME_MAPPER));
	}


	public S getMedicalService() {
		return medicalService;
	}

	public String getMedicalServiceName() {
		S target = getMedicalService();
		return target == null ? null : target.getName();
	}

	void _setMedicalService(MedicalService medicalService) { //NOSONAR
		this.medicalService = (S) medicalService;
	}

	protected void setMedicalService(S medicalService) {
		this.medicalService = Objects.requireNonNull(medicalService, "'medicalService' to set must not be null");
	}


	public boolean isGeneralistic() {
		return GeneralisticDoctor.class.isInstance(this) || hasSpeciality(GeneralMedicine.class); //NOSONAR
	}


	public Set<? super Class<? extends MedicalService>> getSpecialities() { //NOSONAR
		return Collections.unmodifiableSet(specialities);
	}

	@SuppressWarnings("hiding")
	public <D extends Doctor<? extends S>, S extends MedicalService<? super D>> boolean hasSpeciality(Class<S> type) {
		return specialities.contains(type);
	}

	public boolean addSpeciality(Class<? extends MedicalService> speciality) {
		return specialities.add(Objects.requireNonNull(speciality, "'speciality' to add must not be null"));
	}

	public boolean addSpecialities(Class<? extends MedicalService>[] specialities) {
		Objects.requireNonNull("'specialities' to add must not be null");
		boolean changed = false;
		for (Class<? extends MedicalService> speciality : specialities) {
			changed |= addSpeciality(speciality);
		}
		return changed;
	}

	public boolean addSpecialities(Collection<Class<? extends MedicalService>> specialities) {
		Objects.requireNonNull("'specialities' to add must not be null");
		boolean changed = false;
		for (Class<? extends MedicalService> speciality : specialities) {
			changed |= addSpeciality(speciality);
		}
		return changed;
	}


	@Override
	public MedicalAppointment remitPatientTo(Patient patient, Doctor specialist) //NOSONAR
	{
		// configure appointment instance
		MedicalAppointment appointment = new MedicalAppointment(patient, this, specialist);
		appointment.setSubject(String.format("Patient '%s' remitment to '%s' specialist",
				patient.getName(),
				specialist.getMedicalServiceName()
			));
		appointment.addNote(String.format("Patient is remitted to '%s' specialist (%s) of '%s' center by %s '%s'",
				specialist.getMedicalServiceName(),
				specialist.getName(),
				specialist.getMedicalCenterName(),
				getEmployeeType(),
				getName()
			));

		// register instance in targets
		patient.addMedicalAppointment(appointment);
		MedicalCenter medicalCenter = specialist.getMedicalCenter();
		if (medicalCenter.findPatient(patient.getNid()).isEmpty()) {
			medicalCenter.addPatient(patient);
		}
		return appointment;
	}


	@Override
	public MedicalAppointment remitPatientTo(Patient patient, MedicalCenter medicalCenter)
	{
		// configure appointment instance
		MedicalAppointment appointment = new MedicalAppointment(patient, this, medicalCenter);
		appointment.setSubject(String.format("Patient '%s' remitment to center: %s",
				patient.getName(),
				medicalCenter.getName()
			));
		appointment.addNote(String.format("Patient is remitted to center '%s' by %s '%s'",
				medicalCenter.getName(),
				getEmployeeType(),
				getName()
			));

		// register instance in targets
		patient.addMedicalAppointment(appointment);
		if (medicalCenter.findPatient(patient.getNid()).isEmpty()) {
			medicalCenter.addPatient(patient);
		}
		return appointment;
	}
}
