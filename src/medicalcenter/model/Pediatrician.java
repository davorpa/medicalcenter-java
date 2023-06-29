package medicalcenter.model;

import java.util.Arrays;
import java.util.Collection;

public class Pediatrician extends Doctor<Pediatricts>
{
	public static final String MEDICAL_EMPLOYEE_TYPE = "Pediatrician";


	public Pediatrician(String nid, String name) {
		super(nid, name);
		addSpeciality(Pediatricts.class);
	}

	public Pediatrician(String nid, String name, String phoneNumber) {
		super(nid, name, phoneNumber);
		addSpeciality(Pediatricts.class);
	}

	public Pediatrician(String nid, String name,
			Class<? extends MedicalService>[] otherSpecialities) {
		this(nid, name, Arrays.asList(otherSpecialities));
	}

	public Pediatrician(String nid, String name,
			Collection<Class<? extends MedicalService>> otherSpecialities) {
		this(nid, name);
		addSpecialities(otherSpecialities);
	}

	public Pediatrician(String nid, String name, String phoneNumber,
			Class<? extends MedicalService>[] otherSpecialities) {
		this(nid, name, phoneNumber, Arrays.asList(otherSpecialities));
	}

	public Pediatrician(String nid, String name, String phoneNumber,
			Collection<Class<? extends MedicalService>> otherSpecialities) {
		this(nid, name, phoneNumber);
		addSpecialities(otherSpecialities);
	}


	@Override
	public String getEmployeeType() {
		return MEDICAL_EMPLOYEE_TYPE;
	}
}
