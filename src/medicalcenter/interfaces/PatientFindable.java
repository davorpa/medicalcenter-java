package medicalcenter.interfaces;

import java.util.Optional;

import medicalcenter.model.Patient;

public interface PatientFindable {

	Optional<Patient> findPatient(String nid);

}