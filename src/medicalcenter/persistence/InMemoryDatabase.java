package medicalcenter.persistence;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import medicalcenter.model.BloodType;
import medicalcenter.model.Doctor;
import medicalcenter.model.MedicalCenter;
import medicalcenter.model.MedicalService;
import medicalcenter.model.Patient;
import medicalcenter.util.DuplicateElementException;

public class InMemoryDatabase implements Database
{
	private final Set<MedicalCenter> medicalCenters = new LinkedHashSet<>();

	private final Set<Doctor> doctors = new LinkedHashSet<>(); //NOSONAR

	private final Set<Patient> patients = new LinkedHashSet<>();


	public InMemoryDatabase() {
		super();
	}


	@Override
	public Optional<MedicalCenter> findMedicalCenter(String code)
	{
		Objects.requireNonNull(code, "'code' to find must not be null");
		for (MedicalCenter medicalCenter : medicalCenters) {
			if (Objects.equals(code, medicalCenter.getCode())) {
				return Optional.of(medicalCenter);
			}
		}
		return Optional.empty();
	}

	@Override
	public List<MedicalCenter> listAllMedicalCenters()
	{
		return new LinkedList<>(medicalCenters);
	}

	@Override
	public <E extends MedicalCenter> E save(E entity)
	{
		Objects.requireNonNull(entity, "'entity' to save must not be null");
		if (medicalCenters.add(entity)) {
			doctors.addAll(entity.listAllDoctors());
			patients.addAll(entity.getPatients());
			return entity;
		}
		throw new DuplicateElementException(entity);
	}


	@Override
	public Optional<Doctor> findDoctor(String nid) //NOSONAR
	{
		Objects.requireNonNull(nid, "'nid' to find must not be null");
		for (Doctor doctor : doctors) { //NOSONAR
			if (Objects.equals(nid, doctor.getNid())) {
				return Optional.of(doctor);
			}
		}
		return Optional.empty();
	}

	@Override
	public List<Doctor> listAllDoctors()
	{
		return new LinkedList<>(doctors);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <D extends Doctor<? extends S>, S extends MedicalService<? super D>> List<D> listAllDoctorsByMedicalService(
			Class<S> type)
	{
		Objects.requireNonNull(type, "'type' to find must not be null");
		List<D> list = new LinkedList<>();
		for (Doctor doctor : doctors) { //NOSONAR
			MedicalService service = doctor.getMedicalService(); //NOSONAR
			if (type.isInstance(service)) {
				list.addAll(service.getDoctors());
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <D extends Doctor<? extends S>, S extends MedicalService<? super D>> List<Doctor> listAllDoctorsBySpeciality(
			Class<S> type)
	{
		List<Doctor> list = new LinkedList<>(); //NOSONAR
		for (Doctor doctor : doctors) { //NOSONAR
			if (doctor.hasSpeciality(type) ) {
				list.add(doctor);
			}
		}
		return list;
	}

	@Override
	public List<Doctor> listAllGeneralisticDoctors()
	{
		List<Doctor> list = new LinkedList<>(); //NOSONAR
		for (Doctor doctor : doctors) { //NOSONAR
			if (doctor.isGeneralistic() ) {
				list.add(doctor);
			}
		}
		return list;
	}

	@Override
	public <E extends Doctor> E save(E entity)
	{
		Objects.requireNonNull(entity, "'entity' to save must not be null");
		if (doctors.add(entity)) {
			return entity;
		}
		throw new DuplicateElementException(entity);
	}


	@Override
	public Optional<Patient> findPatient(String nid)
	{
		Objects.requireNonNull(nid, "'nid' to find must not be null");
		for (Patient patient : patients) { //NOSONAR
			if (Objects.equals(nid, patient.getNid())) {
				return Optional.of(patient);
			}
		}
		return Optional.empty();
	}

	@Override
	public List<Patient> listAllPatients()
	{
		return new LinkedList<>(patients);
	}

	@Override
	public List<Patient> listAllPatientsByAgeRange(int start, int end)
	{
		List<Patient> list = new LinkedList<>();
		LocalDate now = LocalDate.now();
		for (Patient patient : patients) {
			LocalDate birthDate = patient.getBirthDate();
			long age = ChronoUnit.YEARS.between(birthDate, now);
			if (age >= start && age <= end) {
				list.add(patient);
			}
		}
		return list;
	}

	@Override
	public List<Patient> listAllPatientsByBloodType(BloodType bloodType)
	{
		List<Patient> list = new LinkedList<>();
		for (Patient patient : patients) {
			if (Objects.equals(bloodType, patient.getBloodType())) {
				list.add(patient);
			}
		}
		return list;
	}

	@Override
	public <E extends Patient> E save(E entity)
	{
		Objects.requireNonNull(entity, "'entity' to save must not be null");
		if (patients.add(entity)) {
			return entity;
		}
		throw new DuplicateElementException(entity);
	}

}
