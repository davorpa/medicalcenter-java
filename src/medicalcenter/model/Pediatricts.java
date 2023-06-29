package medicalcenter.model;

public class Pediatricts extends MedicalService<Pediatrician>
{
	public static final String MEDICAL_SERVICE_NAME = "Pediatricts";


	public Pediatricts() {
		super(MEDICAL_SERVICE_NAME);
	}
}
