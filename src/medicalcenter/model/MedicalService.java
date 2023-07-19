package medicalcenter.model;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import medicalcenter.interfaces.DoctorFindable;
import medicalcenter.interfaces.DoctorListable;
import medicalcenter.util.DuplicateElementException;

public abstract class MedicalService<D extends Doctor<? extends MedicalService>>
		implements DoctorFindable, DoctorListable
{
	public static final Function<Class<?>, String> MEDICAL_SERVICE_NAME_MAPPER = (Class<?> type) -> {
		try {
			Field field = type.getField("MEDICAL_SERVICE_NAME");
			Object fieldValue = field.get(null); // get static final value
			return String.valueOf(fieldValue); // convert
		} catch (Exception e) { //NOSONAR
		}
		return type.getSimpleName();
	};


	private MedicalCenter medicalCenter;

	private String name;

	private final List<D> doctors = new LinkedList<>();

	private final Class<D> doctorsClazz;


	@SuppressWarnings("unchecked")
	protected MedicalService(String name) {
		super();
		this.doctorsClazz = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		setName(name);
	}

	protected MedicalService(MedicalCenter medicalCenter, String name) {
		this(name);
		setMedicalCenter(medicalCenter);
	}


	@Override
	public int hashCode() {
		return Objects.hash(getClass(), getName());
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		MedicalService<?> other = (MedicalService<?>) obj;
		return Objects.equals(getName(), other.getName());
	}


	@Override
	public String toString() {
		return String.format("MedicalService (name=%s, doctors=%s)", name, doctors.size());
	}


	public MedicalCenter getMedicalCenter() {
		return this.medicalCenter;
	}

	void _setMedicalCenter(MedicalCenter medicalCenter) { //NOSONAR
		this.medicalCenter = medicalCenter;
		for (D doctor : doctors) {
			doctor._setMedicalCenter(medicalCenter);
		}
	}

	protected void setMedicalCenter(MedicalCenter medicalCenter) {
		this.medicalCenter = Objects.requireNonNull(medicalCenter, "'medicalCenter' to set must not be null");
		for (D doctor : doctors) {
			doctor.setMedicalCenter(medicalCenter);
		}
	}


	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = Objects.requireNonNull(name, "'name' to set must not be null");
	}


	public List<D> getDoctors() {
		return Collections.unmodifiableList(doctors);
	}

	protected Class<D> getDoctorsClazz() {
		return doctorsClazz;
	}

	public boolean addDoctor(D doctor) {
		Objects.requireNonNull(doctor, "'doctor' to add must not be null");
		if (doctors.contains(doctor)) {
			throw new DuplicateElementException(doctor);
		}
		doctor._setMedicalService(this); //link
		return medicalCenter.addEmployee(doctor) && doctors.add(doctor);
	}

	public boolean removeDoctor(D doctor) {
		boolean removed = doctors.remove(doctor);
		if (removed) {
			removed |= medicalCenter.removeEmployee(doctor);
			doctor._setMedicalService(null); //unlink
		}
		return removed;
	}


	@Override
	public Optional<D> findDoctor(String nid) {
		Objects.requireNonNull(nid, "'nid' to find must not be null");
		for (D doctor : doctors) {
			if (Objects.equals(nid, doctor.getNid())) {
				return Optional.of(doctor);
			}
		}
		return Optional.empty();
	}


	@Override
	public List<D> listAllDoctors() {
		return new LinkedList<>(doctors);
	}

	@SuppressWarnings({ "unchecked", "hiding", "rawtypes" })
	@Override
	public <D extends Doctor<? extends S>, S extends MedicalService<? super D>> List<D> listAllDoctorsByMedicalService( //NOSONAR
			Class<S> type)
	{
		Objects.requireNonNull(type, "'type' to find must not be null");
		if (type.isInstance(this)) return new LinkedList(doctors);
		return new ArrayList<>(0);
	}


	@Override
	public <T extends Doctor<? extends S>, S extends MedicalService<? super T>> List<Doctor> listAllDoctorsBySpeciality( //NOSONAR
			Class<S> type)
	{
		Objects.requireNonNull(type, "'type' to find must not be null");
		List<Doctor> list = new LinkedList<>(); //NOSONAR
		for (D doctor : doctors) {
			if (doctor.hasSpeciality(type) ) {
				list.add(doctor);
			}
		}
		return list;
	}


	@Override
	public List<Doctor> listAllGeneralisticDoctors() { //NOSONAR
		List<Doctor> list = new LinkedList<>(); //NOSONAR
		for (D doctor : doctors) {
			if (doctor.isGeneralistic()) {
				list.add(doctor);
			}
		}
		return list;
	}
}
