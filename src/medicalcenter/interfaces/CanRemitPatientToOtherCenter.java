package medicalcenter.interfaces;

import medicalcenter.model.MedicalAppointment;
import medicalcenter.model.MedicalCenter;
import medicalcenter.model.Patient;

public interface CanRemitPatientToOtherCenter
{
	MedicalAppointment remitPatientTo(
			Patient patient,
			MedicalCenter medicalCenter);
}
