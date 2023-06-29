package medicalcenter.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Patient extends Person
{
	private LocalDate birthDate;

	private BloodType bloodType;

	private final List<MedicalAppointment> medicalAppointments = new LinkedList<>();


	public Patient(String nid, String name, LocalDate birthDate, BloodType bloodType) {
		super(nid, name);
		setBirthDate(birthDate);
		setBloodType(bloodType);
	}

	public Patient(String nid, String name, String phoneNumber, LocalDate birthDate, BloodType bloodType) {
		super(nid, name, phoneNumber);
		setBirthDate(birthDate);
		setBloodType(bloodType);
	}


	@Override
	protected String defineAttrs() {
		return String.format(
				"%s, birthDate=%s, bloodType=%s",
				super.defineAttrs(),
				getBirthDate(),
				getBloodType());
	}


	public LocalDate getBirthDate() {
		return birthDate;
	}

	protected void setBirthDate(LocalDate birthDate) {
		this.birthDate = Objects.requireNonNull(birthDate, "'birthDate' to set must not be null");
	}


	public long getAgeInYears(Temporal nowRef) {
		return ChronoUnit.YEARS.between(getBirthDate(), Objects.requireNonNull(nowRef, "'nowRef' must not be null"));
	}


	public BloodType getBloodType() {
		return bloodType;
	}

	protected void setBloodType(BloodType bloodType) {
		this.bloodType = Objects.requireNonNull(bloodType, "'bloodType' to set must not be null");
	}


	public List<MedicalAppointment> getMedicalAppointments() {
		return Collections.unmodifiableList(medicalAppointments);
	}

	public void addMedicalAppointment(MedicalAppointment appointment) {
		appointment.setPatient(this);
		medicalAppointments.add(appointment);
	}
}
