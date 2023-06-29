package medicalcenter.model;

import java.util.Objects;

public abstract class Person
{
	private String nid;

	private String name;

	private String phoneNumber;


	protected Person(String nid, String name) {
		super();
		setNid(nid);
		setName(name);
	}

	protected Person(String nid, String name, String phoneNumber) {
		super();
		setNid(nid);
		setName(name);
		setPhoneNumber(phoneNumber);
	}


	@Override
	public int hashCode() {
		return Objects.hash(getClass(), getNid());
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Person other = (Person) obj;
		return Objects.equals(getNid(), other.getNid());
	}


	@Override
	public String toString() {
		Class<? extends Person> type = getClass();
		String typeName = type.getSimpleName();
		return String.format("%s (%s)", typeName, defineAttrs());
	}


	protected String defineAttrs() {
		return String.format(
				"nid=%s, name=%s, phoneNumber=%s",
				getNid(),
				getName(),
				getPhoneNumber());
	}


	public String getNid() {
		return nid;
	}

	protected void setNid(String nid) {
		this.nid = Objects.requireNonNull(nid, "'nid' to set must not be null");
	}


	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = Objects.requireNonNull(name, "'name' to set must not be null");
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	protected void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = Objects.requireNonNull(phoneNumber, "'phoneNumber' to set must not be null");
	}
}
