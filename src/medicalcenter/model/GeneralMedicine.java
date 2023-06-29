package medicalcenter.model;

public class GeneralMedicine extends MedicalService<GeneralisticDoctor>
{
	public static final String MEDICAL_SERVICE_NAME = "General Medicine";


	public GeneralMedicine() {
		super(MEDICAL_SERVICE_NAME);
	}
}
