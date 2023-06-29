package medicalcenter.model;

public class Nurse
		extends MedicalEmployee
{
	public static final String MEDICAL_EMPLOYEE_TYPE = "Nurse";


	public Nurse(String nid, String name) {
		super(nid, name);
	}

	public Nurse(String nid, String name, String phoneNumber) {
		super(nid, name, phoneNumber);
	}


	@Override
	public String getEmployeeType() {
		return MEDICAL_EMPLOYEE_TYPE;
	}
}
