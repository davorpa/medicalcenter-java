package medicalcenter.model;

public enum BloodType
{
	A_POSITIVE("A+"),
	A_NEGATIVE("A-"),

	B_POSITIVE("B+"),
	B_NEGATIVE("B+"),

	ZERO_POSITIVE("O+"),
	ZERO_NEGATIVE("O-"),

	AB_POSITIVE("AB+"),
	AB_NEGATIVE("AB-");


	public final String label;


	BloodType(String label) {
		this.label = label;
	}


	public String getLabel() {
		return label;
	}


	@Override
	public String toString() {
		return label;
	}
}
