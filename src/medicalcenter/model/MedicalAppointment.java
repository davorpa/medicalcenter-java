package medicalcenter.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MedicalAppointment
{
	private final Instant createdAt = Instant.now();

	private String subject;

	private LocalDateTime appointmentDate;

	private Patient patient;

	private MedicalCenter fromCenter;

	private MedicalEmployee by;

	private MedicalCenter toCenter;

	private MedicalEmployee to;

	private List<String> notes = new LinkedList<>();


	MedicalAppointment(
			Patient patient, MedicalEmployee by)
	{
		super();
		setPatient(patient);
		setBy(by);
		setFromCenter(by.getMedicalCenter());
	}

	protected MedicalAppointment(
			Patient patient, MedicalEmployee by,
			MedicalEmployee to)
	{
		this(patient, by);
		setTo(to);
		setToCenter(to.getMedicalCenter());
	}

	protected MedicalAppointment(
			Patient patient, MedicalEmployee by,
			MedicalCenter toCenter)
	{
		this(patient, by);
		setToCenter(toCenter);
	}


	public Instant getCreatedAt() {
		return createdAt;
	}


	public String getSubject() {
		return subject;
	}

	protected void setSubject(String subject) {
		this.subject = subject;
	}


	public LocalDateTime getAppointmentDate() {
		return appointmentDate;
	}

	protected void setAppointmentDate(LocalDateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
	}


	public Patient getPatient() {
		return patient;
	}

	public String getPatientName() {
		Patient target = getPatient();
		return target == null ? null : target.getName();
	}

	protected void setPatient(Patient patient) {
		// emulate a final field behavior but with lazily support
		Objects.requireNonNull(patient, "'patient' to treat must not be null");
		synchronized (this) {
			if (this.patient != null && this.patient != patient) {
				throw new IllegalStateException("'patient' can not be reasigned since it is a final field");
			}
			this.patient = patient;
		}
	}


	public MedicalCenter getFromCenter() {
		return fromCenter;
	}

	public String getFromCenterName() {
		MedicalCenter target = getFromCenter();
		return target == null ? null : target.getName();
	}

	protected void setFromCenter(MedicalCenter fromCenter) {
		// emulate a final field behavior but with lazily support
		Objects.requireNonNull(fromCenter, "'medicalCenter' from where remit must not be null");
		synchronized (this) {
			if (this.fromCenter != null && this.fromCenter != fromCenter) {
				throw new IllegalStateException("'medicalCenter' from where remit can not be reasigned since it is a final field");
			}
			this.fromCenter = fromCenter;
		}
	}


	public MedicalEmployee getBy() {
		return by;
	}

	public String getByName() {
		MedicalEmployee target = getBy();
		return target == null ? null : target.getName();
	}

	protected void setBy(MedicalEmployee by) {
		// emulate a final field behavior but with lazily support
		Objects.requireNonNull(by, "'medicalCenter employee' from whom remit must not be null");
		synchronized (this) {
			if (this.by != null && this.by != by) {
				throw new IllegalStateException("'medicalCenter employee' from whom remit can not be reasigned since it is a final field");
			}
			this.by = by;
		}
	}


	public MedicalCenter getToCenter() {
		return toCenter;
	}

	public String getToCenterName() {
		MedicalCenter target = getToCenter();
		return target == null ? null : target.getName();
	}

	protected void setToCenter(MedicalCenter toCenter) {
		// emulate a final field behavior but with lazily support
		Objects.requireNonNull(toCenter, "'medicalCenter' to where remit must not be null");
		synchronized (this) {
			if (this.toCenter != null && this.toCenter != toCenter) {
				throw new IllegalStateException("'medicalCenter' to where remit can not be reasigned since it is a final field");
			}
			this.toCenter = toCenter;
		}
	}


	public MedicalEmployee getTo() {
		return to;
	}

	public String getToName() {
		MedicalEmployee target = getTo();
		return target == null ? null : target.getName();
	}

	protected void setTo(MedicalEmployee to) {
		// emulate a final field behavior but with lazily support
		Objects.requireNonNull(to, "'medicalCenter employee' to whom remit must not be null");
		synchronized (this) {
			if (this.to != null && this.to != to) {
				throw new IllegalStateException("'medicalCenter employee' to whom remit can not be reasigned since it is a final field");
			}
			this.to = to;
		}
	}


	public List<String> getNotes() {
		return Collections.unmodifiableList(notes);
	}

	public boolean addNote(String note) {
		return this.notes.add(note);
	}
}
