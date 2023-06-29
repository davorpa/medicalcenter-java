package medicalcenter.model;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Function;

public abstract class MedicalEmployee extends Person
{
	public static final Function<Class<?>, String> MEDICAL_EMPLOYEE_TYPE_MAPPER = (Class<?> clazz) -> {
		try {
			Field field = clazz.getField("MEDICAL_EMPLOYEE_TYPE");
			Object fieldValue = field.get(null); // get static final value
			return String.valueOf(fieldValue); // convert
		} catch (Exception e) { //NOSONAR
		}
		return clazz.getSimpleName();
	};


	private MedicalCenter medicalCenter;


	protected MedicalEmployee(String nid, String name) {
		super(nid, name);
	}

	protected MedicalEmployee(String nid, String name, String phoneNumber) {
		super(nid, name, phoneNumber);
	}


	@Override
	protected String defineAttrs() {
		return String.format(
				"%s, type=%s, center=%s-%s",
				super.defineAttrs(),
				getEmployeeType(),
				getMedicalCenterCode(),
				getMedicalCenterName());
	}


	public abstract String getEmployeeType();


	public MedicalCenter getMedicalCenter() {
		return this.medicalCenter;
	}

	public String getMedicalCenterCode() {
		MedicalCenter target = getMedicalCenter();
		return target == null ? null : target.getCode();
	}

	public String getMedicalCenterName() {
		MedicalCenter target = getMedicalCenter();
		return target == null ? null : target.getName();
	}

	void _setMedicalCenter(MedicalCenter medicalCenter) { //NOSONAR
		this.medicalCenter = medicalCenter;
	}

	protected void setMedicalCenter(MedicalCenter medicalCenter) {
		this.medicalCenter = Objects.requireNonNull(medicalCenter, "'medicalCenter' to set must not be null");
	}
}
