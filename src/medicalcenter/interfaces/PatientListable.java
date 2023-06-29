package medicalcenter.interfaces;

import java.util.List;

import medicalcenter.model.BloodType;
import medicalcenter.model.Patient;

public interface PatientListable {

	List<Patient> listAllPatients();

	List<Patient> listAllPatientsByAgeRange(int start, int end);

	List<Patient> listAllPatientsByBloodType(BloodType bloodType);

}