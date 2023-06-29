package medicalcenter.reports;

import java.util.Collection;

import medicalcenter.model.Doctor;
import medicalcenter.model.Patient;

public interface ReportGenerator
{
	void generateDoctorReport(Doctor doctor); //NOSONAR


	void generateDoctorsReport(
			String reportTitle,
			Collection<? extends Doctor> doctors); //NOSONAR


	void generatePatientReport(Patient patient);


	void generatePatientsReport(
			String reportTitle,
			Collection<? extends Patient> patients);
}
