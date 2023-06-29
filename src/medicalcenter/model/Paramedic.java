package medicalcenter.model;

import medicalcenter.interfaces.CanRemitPatientToOtherCenter;

public class Paramedic
		extends MedicalEmployee
		implements CanRemitPatientToOtherCenter
{
	public static final String MEDICAL_EMPLOYEE_TYPE = "Paramedic";


	public Paramedic(String nid, String name) {
		super(nid, name);
	}

	public Paramedic(String nid, String name, String phoneNumber) {
		super(nid, name, phoneNumber);
	}


	@Override
	public String getEmployeeType() {
		return MEDICAL_EMPLOYEE_TYPE;
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
