package medicalcenter.interfaces;

import medicalcenter.model.Doctor;
import medicalcenter.model.MedicalAppointment;
import medicalcenter.model.Patient;

public interface CanRemitPatientToSpecialist
{
	MedicalAppointment remitPatientTo(
			Patient patient,
			Doctor specialist //NOSONAR
		);
}
