package medicalcenter.model;

import medicalcenter.interfaces.CanRemitPatientToOtherCenter;

public class HeadNurse
		extends Nurse
		implements CanRemitPatientToOtherCenter
{
	public HeadNurse(String nid, String name) {
		super(nid, name);
	}

	public HeadNurse(String nid, String name, String phoneNumber) {
		super(nid, name, phoneNumber);
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
