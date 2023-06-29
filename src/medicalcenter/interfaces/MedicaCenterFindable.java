package medicalcenter.interfaces;

import java.util.Optional;

import medicalcenter.model.MedicalCenter;

public interface MedicaCenterFindable {

	Optional<MedicalCenter> findMedicalCenter(String code);

}