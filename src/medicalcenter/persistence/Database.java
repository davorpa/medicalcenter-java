package medicalcenter.persistence;

import medicalcenter.interfaces.DoctorFindable;
import medicalcenter.interfaces.DoctorListable;
import medicalcenter.interfaces.MedicaCenterFindable;
import medicalcenter.interfaces.MedicalCenterListable;
import medicalcenter.interfaces.PatientFindable;
import medicalcenter.interfaces.PatientListable;
import medicalcenter.model.Doctor;
import medicalcenter.model.MedicalCenter;
import medicalcenter.model.Patient;

public interface Database extends
		MedicaCenterFindable, MedicalCenterListable,
		DoctorFindable, DoctorListable,
		PatientFindable, PatientListable
{
	<T extends MedicalCenter> T save(T entity);


	<T extends Doctor> T save(T entity);


	<T extends Patient> T save(T entity);
}
