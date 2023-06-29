package medicalcenter.interfaces;

import java.util.Optional;

import medicalcenter.model.Doctor;

public interface DoctorFindable {

	Optional<? extends Doctor> findDoctor(String nid); //NOSONAR

}