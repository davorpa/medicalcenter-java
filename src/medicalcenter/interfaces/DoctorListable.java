package medicalcenter.interfaces;

import java.util.List;

import medicalcenter.model.Doctor;
import medicalcenter.model.MedicalService;

public interface DoctorListable {

	List<? extends Doctor> listAllDoctors(); //NOSONAR

	<D extends Doctor<? extends S>, S extends MedicalService<? super D>> List<D> listAllDoctorsByMedicalService(
			Class<S> type);

	<D extends Doctor<? extends S>, S extends MedicalService<? super D>> List<Doctor> listAllDoctorsBySpeciality( //NOSONAR
			Class<S> type);

	List<? extends Doctor> listAllGeneralisticDoctors(); //NOSONAR

}