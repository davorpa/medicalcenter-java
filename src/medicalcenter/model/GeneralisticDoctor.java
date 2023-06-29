package medicalcenter.model;

import java.util.Arrays;
import java.util.Collection;

public class GeneralisticDoctor extends Doctor<GeneralMedicine>
{
	public static final String MEDICAL_EMPLOYEE_TYPE = "Generalistic Doctor";


	public GeneralisticDoctor(String nid, String name) {
		super(nid, name);
		addSpeciality(GeneralMedicine.class);
	}

	public GeneralisticDoctor(String nid, String name, String phoneNumber) {
		super(nid, name, phoneNumber);
		addSpeciality(GeneralMedicine.class);
	}

	public GeneralisticDoctor(String nid, String name,
			Class<? extends MedicalService>[] otherSpecialities) {
		this(nid, name, Arrays.asList(otherSpecialities));
	}

	public GeneralisticDoctor(String nid, String name,
			Collection<Class<? extends MedicalService>> otherSpecialities) {
		this(nid, name);
		addSpecialities(otherSpecialities);
	}

	public GeneralisticDoctor(String nid, String name, String phoneNumber,
			Class<? extends MedicalService>[] otherSpecialities) {
		this(nid, name, phoneNumber, Arrays.asList(otherSpecialities));
	}

	public GeneralisticDoctor(String nid, String name, String phoneNumber,
			Collection<Class<? extends MedicalService>> otherSpecialities) {
		this(nid, name, phoneNumber);
		addSpecialities(otherSpecialities);
	}


	@Override
	public boolean isGeneralistic() {
		return true;
	}


	@Override
	public String getEmployeeType() {
		return MEDICAL_EMPLOYEE_TYPE;
	}
}
