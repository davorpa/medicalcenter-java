package medicalcenter.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import medicalcenter.interfaces.DoctorFindable;
import medicalcenter.interfaces.DoctorListable;
import medicalcenter.interfaces.PatientFindable;
import medicalcenter.interfaces.PatientListable;
import medicalcenter.util.DuplicateElementException;

public class MedicalCenter implements
		DoctorFindable, DoctorListable,
		PatientFindable, PatientListable
{
	private String code;

	private String name;

	private final List<MedicalService<? extends Doctor>> services = new ArrayList<>();

	private final List<MedicalEmployee> employees = new LinkedList<>();

	private final List<Patient> patients = new LinkedList<>();


	public MedicalCenter(String code, String name) {
		super();
		setCode(code);
		setName(name);
	}


	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof MedicalCenter)) return false;
		MedicalCenter other = (MedicalCenter) obj;
		return Objects.equals(getCode(), other.getCode());
	}


	@Override
	public String toString() {
		return String.format("MedicalCenter (code=%s, name=%s, services=%s, employees=%s, patients=%s)",
				getCode(), getName(),
				services.size(),
				employees.size(),
				patients.size());
	}


	public String getCode() {
		return code;
	}

	protected void setCode(String code) {
		this.code = Objects.requireNonNull(code, "'code' to set must not be null");
	}


	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = Objects.requireNonNull(name, "'name' to set must not be null");
	}


	public List<MedicalService<? extends Doctor>> getMedicalServices() { //NOSONAR
		return Collections.unmodifiableList(services);
	}

	public <T extends Doctor<S>, S extends MedicalService<? extends T>> boolean addMedicalService(S service) {
		Objects.requireNonNull(service, "'service' to add must not be null");
		if (services.contains(service)) {
			throw new DuplicateElementException(service);
		}
		service._setMedicalCenter(this); // link
		return services.add(service);
	}

	public boolean removeMedicalService(MedicalService<?> service) {
		boolean removed = services.remove(service);
		if (removed) {
			service._setMedicalCenter(null); // unlink
		}
		return removed;
	}


	public List<MedicalEmployee> getEmployees() {
		return Collections.unmodifiableList(employees);
	}

	public boolean addEmployee(MedicalEmployee employee) {
		Objects.requireNonNull(employee, "'employee' to add must not be null");
		if (employees.contains(employee)) {
			throw new DuplicateElementException(employee);
		}
		employee._setMedicalCenter(this); // link
		return employees.add(employee);
	}

	public boolean removeEmployee(MedicalEmployee employee) {
		boolean removed = employees.remove(employee);
		if (removed) {
			employee._setMedicalCenter(null); // unlink
		}
		return removed;
	}


	public List<Patient> getPatients() {
		return Collections.unmodifiableList(patients);
	}

	protected boolean addPatient(Patient patient) {
		Objects.requireNonNull(patient, "'patient' to add must not be null");
		if (patients.contains(patient)) {
			throw new DuplicateElementException(patient);
		}
		return patients.add(patient);
	}


	@Override
	public Optional<? extends Doctor> findDoctor(String nid) { //NOSONAR
		Objects.requireNonNull(nid, "'nid' to find must not be null");
		for (MedicalService<? extends Doctor> service : services) {
			Optional<? extends Doctor> result = service.findDoctor(nid);
			if (result.isPresent()) {
				return result;
			}
		}
		return Optional.empty();
	}


	@Override
	public List<Doctor> listAllDoctors() { //NOSONAR
		List<Doctor> list = new LinkedList<>(); //NOSONAR
		for (MedicalService<? extends Doctor> service : services) {
			list.addAll(service.getDoctors());
		}
		return list;
	}


	@Override
	@SuppressWarnings("unchecked")
	public <D extends Doctor<? extends S>, S extends MedicalService<? super D>> List<D> listAllDoctorsByMedicalService(
			Class<S> type)
	{
		Objects.requireNonNull(type, "'type' to find must not be null");
		List<D> list = new LinkedList<>();
		for (MedicalService service : services) { //NOSONAR
			if (type.isInstance(service)) {
				list.addAll(service.getDoctors());
			}
		}
		return list;
	}


	@Override
	public <D extends Doctor<? extends S>, S extends MedicalService<? super D>> List<Doctor> listAllDoctorsBySpeciality( //NOSONAR
			Class<S> type)
	{
		List<Doctor> list = new LinkedList<>(); //NOSONAR
		for (MedicalService<? extends Doctor> service : services) {
			list.addAll(service.listAllDoctorsBySpeciality(type));
		}
		return list;
	}


	@Override
	public List<Doctor> listAllGeneralisticDoctors() { //NOSONAR
		List<Doctor> list = new LinkedList<>(); //NOSONAR
		for (MedicalService<? extends Doctor> service : services) {
			list.addAll(service.listAllGeneralisticDoctors());
		}
		return list;
	}


	@Override
	public Optional<Patient> findPatient(String nid) {
		Objects.requireNonNull(nid, "'nid' to find must not be null");
		for (Patient patient : patients) {
			if (Objects.equals(nid, patient.getNid())) {
				return Optional.of(patient);
			}
		}
		return Optional.empty();
	}


	@Override
	public List<Patient> listAllPatients()
	{
		return new LinkedList<>(patients);
	}


	@Override
	public List<Patient> listAllPatientsByAgeRange(int start, int end)
	{
		List<Patient> list = new LinkedList<>();
		Instant now = Instant.now();
		for (Patient patient : patients) {
			long age = patient.getAgeInYears(now);
			if (age >= start && age <= end) {
				list.add(patient);
			}
		}
		return list;
	}


	@Override
	public List<Patient> listAllPatientsByBloodType(BloodType bloodType)
	{
		List<Patient> list = new LinkedList<>();
		for (Patient patient : patients) {
			if (Objects.equals(bloodType, patient.getBloodType())) {
				list.add(patient);
			}
		}
		return list;
	}
}
