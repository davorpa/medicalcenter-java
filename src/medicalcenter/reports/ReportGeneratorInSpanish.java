package medicalcenter.reports;

import java.io.PrintStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

import medicalcenter.model.Doctor;
import medicalcenter.model.MedicalAppointment;
import medicalcenter.model.MedicalService;
import medicalcenter.model.Patient;
import medicalcenter.util.Collections;
import medicalcenter.util.Strings;

public class ReportGeneratorInSpanish implements ReportGenerator
{
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("dd-MM-yyyy hh:mm:ss")
			.withZone(ZoneId.systemDefault());


	@Override
	public void generateDoctorReport(Doctor doctor)
	{
		final int pageWidth = 138;
		final String SIDE_FILL_TPL = "| %s |%n";
		final String emptyFilledLine = Strings.repeat(Strings.SPACE, pageWidth - 4);
		final String fullFilledLine = Strings.repeat("=", pageWidth);

		final PrintStream out = System.out;

		out.println();
		out.println(fullFilledLine);
		out.format(SIDE_FILL_TPL, Strings.center("Detalle Doctor", pageWidth - 4));
		out.println(fullFilledLine);

		out.format(SIDE_FILL_TPL, emptyFilledLine);
		if (doctor == null) {
			out.format(SIDE_FILL_TPL, Strings.center("WARN: doctor sin datos", pageWidth - 4));
		} else {
			String LINE_TPL = "| %20s:   %-110s |%n";
			out.format(LINE_TPL, "NID", doctor.getNid());
			out.format(LINE_TPL, "Nombre", doctor.getName());
			out.format(LINE_TPL, "Núm. Telefónico", Objects.toString(doctor.getPhoneNumber(), Strings.EMPTY));
			out.format(LINE_TPL, "Tipo", doctor.getEmployeeType());
			out.format(LINE_TPL, "Centro Médico", doctor.getMedicalCenterName());
			out.format(LINE_TPL, "Servicio Médico", doctor.getMedicalServiceName());
			out.format(LINE_TPL, "Especialidades", Collections.toString(doctor.getSpecialities(),
					MedicalService.MEDICAL_SERVICE_NAME_MAPPER, false));
		}
		out.format(SIDE_FILL_TPL, emptyFilledLine);

		out.println(fullFilledLine);
		out.println();
	}


	@Override
	public void generateDoctorsReport(
			String reportTitle,
			Collection<? extends Doctor> doctors)
	{
		final int pageWidth = 138;
		final String SIDE_FILL_TPL = "| %s |%n";
		final String emptyFilledLine = Strings.repeat(Strings.SPACE, pageWidth - 4);
		final String fullFilledLine = Strings.repeat("=", pageWidth);

		final PrintStream out = System.out;

		out.println();
		out.println(fullFilledLine);
		out.format(SIDE_FILL_TPL, Strings.center(reportTitle, pageWidth - 4));
		out.println(fullFilledLine);

		if (doctors == null || doctors.isEmpty()) {
			out.format(SIDE_FILL_TPL, emptyFilledLine);
			out.format(SIDE_FILL_TPL, Strings.center("INFO: sin doctores", pageWidth - 4));
			out.format(SIDE_FILL_TPL, emptyFilledLine);
		} else {
			String COLS_TPL = "| %-10s | %-20s | %-12s | %-23s | %-20s | %-34s |%n";
			out.format(COLS_TPL,
					Strings.center("NID", 10),
					Strings.center("Nombre", 20),
					Strings.center("Núm.Teléfono", 12),
					Strings.center("Tipo", 23),
					Strings.center("Centro Médico", 20),
					Strings.center("Especialidades", 34));
			out.println(fullFilledLine);
			out.format(SIDE_FILL_TPL, emptyFilledLine);

			for (Doctor doctor : doctors) { //NOSONAR
				out.format(COLS_TPL,
						doctor.getNid(),
						doctor.getName(),
						Objects.toString(doctor.getPhoneNumber(), Strings.EMPTY),
						doctor.getEmployeeType(),
						Objects.toString(doctor.getMedicalCenterName(), Strings.EMPTY),
						Collections.toString(doctor.getSpecialities(),
								MedicalService.MEDICAL_SERVICE_NAME_MAPPER, false));
			}

			out.format(SIDE_FILL_TPL, emptyFilledLine);
		}

		out.println(fullFilledLine);
		out.println();
	}


	@Override
	public void generatePatientReport(Patient patient) {
		final int pageWidth = 128;
		final int tableWidth = 123;
		final String SIDE_FILL_TPL = "| %s |%n";
		final String SIDE_INNER_FILL_TPL = "| %s |";
		final String emptyFilledLine = Strings.repeat(Strings.SPACE, pageWidth - 4);
		final String fullFilledLine = Strings.repeat("=", pageWidth);
		final String tableFilledLine = Strings.repeat("=", tableWidth);
		final String emptyFilledTableLine = Strings.repeat(Strings.SPACE, tableWidth - 4);

		final PrintStream out = System.out;

		out.println();
		out.println(fullFilledLine);
		out.format(SIDE_FILL_TPL, Strings.center("Detalle Paciente", pageWidth - 4));
		out.println(fullFilledLine);

		out.format(SIDE_FILL_TPL, emptyFilledLine);
		if (patient == null) {
			out.format(SIDE_FILL_TPL, Strings.center("WARN: no patient data", pageWidth - 4));
		} else {
			String LINE_TPL = "| %20s:   %-100s |%n";
			out.format(LINE_TPL, "NID", patient.getNid());
			out.format(LINE_TPL, "Nombre", patient.getName());
			out.format(LINE_TPL, "Núm. Telefónico", Objects.toString(patient.getPhoneNumber(), Strings.EMPTY));
			out.format(LINE_TPL, "Fecha Nacimiento", patient.getBirthDate());
			out.format(LINE_TPL, "Grupo Sanguíneo", patient.getBloodType());
			out.format(SIDE_FILL_TPL, emptyFilledLine);
			out.format(SIDE_FILL_TPL, Strings.center(tableFilledLine, pageWidth - 4));
			out.format(SIDE_FILL_TPL, Strings.center(
					String.format(SIDE_INNER_FILL_TPL, Strings.center(
							String.format("Citas Médicas: %d", patient.getMedicalAppointments().size())
							,
							tableWidth - 4))
					,
					pageWidth - 4));
			out.format(SIDE_FILL_TPL, Strings.center(tableFilledLine, pageWidth - 4));
			Collection<MedicalAppointment> appointments = patient.getMedicalAppointments();
			if (appointments == null || appointments.isEmpty()) {
				out.format(SIDE_FILL_TPL, Strings.center(String.format(SIDE_INNER_FILL_TPL, emptyFilledTableLine), pageWidth - 4));
				out.format(SIDE_FILL_TPL, Strings.center(String.format(SIDE_INNER_FILL_TPL, Strings.center(
						"INFO: sin citas médicas", tableWidth - 4)),
						pageWidth - 4));
				out.format(SIDE_FILL_TPL, Strings.center(String.format(SIDE_INNER_FILL_TPL, emptyFilledTableLine), pageWidth - 4));
			} else {
				String row;
				String COLS_TPL = "| %-19s | %-22s | %-22s | %-22s | %-22s |";
				row = String.format(COLS_TPL,
						Strings.center("Fecha", 19),
						Strings.center("Desde el centro", 22),
						Strings.center("Por el facultativo", 22),
						Strings.center("Hacia el centro", 22),
						Strings.center("Al especialista", 22));
				out.format(SIDE_FILL_TPL, Strings.center(row, pageWidth - 4));
				out.format(SIDE_FILL_TPL, Strings.center(tableFilledLine, pageWidth - 4));
				out.format(SIDE_FILL_TPL, Strings.center(String.format(SIDE_INNER_FILL_TPL, emptyFilledTableLine), pageWidth - 4));
				for (MedicalAppointment appointment : appointments) {
					row = String.format(COLS_TPL,
							DATE_TIME_FORMATTER.format(appointment.getCreatedAt()),
							appointment.getFromCenterName(),
							appointment.getByName(),
							appointment.getToCenterName(),
							Objects.toString(appointment.getToName(), Strings.center("N/A", 22)));
					out.format(SIDE_FILL_TPL, Strings.center(row, pageWidth - 4));
				}
				out.format(SIDE_FILL_TPL, Strings.center(String.format(SIDE_INNER_FILL_TPL, emptyFilledTableLine), pageWidth - 4));
			}
			out.format(SIDE_FILL_TPL, Strings.center(tableFilledLine, pageWidth - 4));
		}
		out.format(SIDE_FILL_TPL, emptyFilledLine);

		out.println(fullFilledLine);
		out.println();
	}


	@Override
	public void generatePatientsReport(String reportTitle, Collection<? extends Patient> patients) {

		final int pageWidth = 128;
		final String SIDE_FILL_TPL = "| %s |%n";
		final String emptyFilledLine = Strings.repeat(Strings.SPACE, pageWidth - 4);
		final String fullFilledLine = Strings.repeat("=", pageWidth);

		final PrintStream out = System.out;

		out.println();
		out.println(fullFilledLine);
		out.format(SIDE_FILL_TPL, Strings.center(reportTitle, pageWidth - 4));
		out.println(fullFilledLine);

		if (patients == null || patients.isEmpty()) {
			out.format(SIDE_FILL_TPL, emptyFilledLine);
			out.format(SIDE_FILL_TPL, Strings.center("INFO: empty patients", pageWidth - 4));
			out.format(SIDE_FILL_TPL, emptyFilledLine);
		} else {
			String COLS_TPL = "| %-10s | %-30s | %-15s | %-19s | %-20s | %15s |%n";
			out.format(COLS_TPL,
					Strings.center("NID", 10),
					Strings.center("Nombre", 30),
					Strings.center("Num.Teléfono", 15),
					Strings.center("Fecha Nacimiento", 19),
					Strings.center("Grupo Sanguíneo", 20),
					Strings.center("Citas Médicas", 15));
			out.println(fullFilledLine);
			out.format(SIDE_FILL_TPL, emptyFilledLine);

			for (Patient patient : patients) { //NOSONAR
				out.format(COLS_TPL,
						patient.getNid(),
						patient.getName(),
						Objects.toString(patient.getPhoneNumber(), Strings.EMPTY),
						Strings.center(Objects.toString(patient.getBirthDate(), Strings.EMPTY), 19),
						Strings.center(Objects.toString(patient.getBloodType(), Strings.EMPTY), 20),
						patient.getMedicalAppointments().size());
			}

			out.format(SIDE_FILL_TPL, emptyFilledLine);
		}

		out.println(fullFilledLine);
		out.println();
	}
}
