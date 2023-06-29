package medicalcenter;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import medicalcenter.model.BloodType;
import medicalcenter.model.Doctor;
import medicalcenter.model.GeneralMedicine;
import medicalcenter.model.GeneralisticDoctor;
import medicalcenter.model.HeadNurse;
import medicalcenter.model.MedicalCenter;
import medicalcenter.model.Paramedic;
import medicalcenter.model.Patient;
import medicalcenter.model.Pediatrician;
import medicalcenter.model.Pediatricts;
import medicalcenter.persistence.Database;
import medicalcenter.persistence.InMemoryDatabase;
import medicalcenter.reports.ReportGenerator;
import medicalcenter.reports.ReportGeneratorInEnglish;
import medicalcenter.reports.ReportGeneratorInSpanish;

public class Main {

	public static void main(String[] args) throws InterruptedException
	{
		Database db = new InMemoryDatabase();

		Patient pac1 = db.save(new Patient("699999990", "Henry Lawsson Jr",
				LocalDate.of(2005, 12, 24), BloodType.A_POSITIVE));
		Patient pac2 = db.save(new Patient("699999991", "Clara Larssen",
				LocalDate.of(2006, 6, 1), BloodType.AB_NEGATIVE));
		Patient pac3 = db.save(new Patient("699999992", "Peter Brennen",
				LocalDate.of(2000, 2, 28), BloodType.ZERO_NEGATIVE));

		Doctor doctor1, doctor2; //NOSONAR
		HeadNurse nurse1, nurse2; //NOSONAR
		Paramedic paramed1, paramed2; //NOSONAR

		MedicalCenter mc1 = new MedicalCenter("H0000001", "Saint Jones Hospital");
		Pediatricts ms11 = new Pediatricts();
		GeneralMedicine ms12 = new GeneralMedicine();
		mc1.addMedicalService(ms11);
		mc1.addMedicalService(ms12);

		mc1.addEmployee(nurse1 = new HeadNurse("769999990", "Eve Mendes", "556-458-505")); //NOSONAR
		mc1.addEmployee(paramed1 = new Paramedic("779999990", "Randy McCain", "556-331-856")); //NOSONAR

		ms11.addDoctor(db.save(new Pediatrician(
				"799999990", "Homer White")));
		ms11.addDoctor(db.save(new Pediatrician(
				"799999991", "Bruce Branagan")));
		ms11.addDoctor(db.save(new Pediatrician(
				"799999992", "Tom Takahashi", "555-567-999",
				List.of(Pediatricts.class, GeneralMedicine.class))));
		ms12.addDoctor(db.save(new GeneralisticDoctor(
				"799999993", "Eric Hellman", "555-555-555")));
		ms12.addDoctor((GeneralisticDoctor) (doctor1 = db.save(new GeneralisticDoctor( //NOSONAR
				"799999994", "Wilma Wilkinson"))));
		ms12.addDoctor(db.save(new GeneralisticDoctor(
				"799999995", "Debra Louruage")));
		ms12.addDoctor(db.save(new GeneralisticDoctor(
				"799999996", "Duge O'Brian",
				List.of(Pediatricts.class))));


		MedicalCenter mc2 = new MedicalCenter("MC000001", "Beverly Hills MC");
		Pediatricts ms21 = new Pediatricts();
		GeneralMedicine ms22 = new GeneralMedicine();
		mc2.addMedicalService(ms21);
		mc2.addMedicalService(ms22);

		mc2.addEmployee(nurse2 = new HeadNurse("749999990", "Molly Malone", "556-698-555")); //NOSONAR
		mc2.addEmployee(paramed2 = new Paramedic("759999990", "Clark Humbold", "556-698-787")); //NOSONAR

		ms21.addDoctor((Pediatrician) (doctor2 = db.save(new Pediatrician( //NOSONAR
				"799999997", "Walter Skull"))));


		System.out.println(doctor1.remitPatientTo(pac1, doctor2).getSubject());
		Thread.sleep(1000);
		System.out.println(doctor1.remitPatientTo(pac1, mc2).getSubject());
		System.out.println(doctor2.remitPatientTo(pac2, mc1).getSubject());
		Thread.sleep(1000);
		System.out.println(nurse1.remitPatientTo(pac1, mc2).getSubject());
		System.out.println(nurse2.remitPatientTo(pac1, mc1).getSubject());
		Thread.sleep(1000);
		System.out.println(paramed2.remitPatientTo(pac2, mc1).getSubject());
		System.out.println(paramed1.remitPatientTo(pac3, mc2).getSubject());


		db.save(mc1);
		db.save(mc2);


		ReportGenerator reporter;
		reporter = ThreadLocalRandom.current().nextInt(100) >= 50
				? new ReportGeneratorInEnglish()
				: new ReportGeneratorInSpanish();

		mc1.findDoctor("799999992")
			.ifPresent(reporter::generateDoctorReport);
		mc1.findDoctor("799999994")
			.ifPresent(reporter::generateDoctorReport);
		ms11.findDoctor("799999993")
			.ifPresent(reporter::generateDoctorReport);

		reporter.generateDoctorsReport(
				String.format("REPORT OF ALL DOCTORS AT %s", mc1.getName()),
				mc1.listAllDoctors());
		reporter.generateDoctorsReport(
				String.format("REPORT OF ALL DOCTORS IN PEDIATRICTS AT %s", mc1.getName()),
				mc1.listAllDoctorsByMedicalService(Pediatricts.class));
		reporter.generateDoctorsReport(
				String.format("REPORT OF ALL SPECIALIZED DOCTORS IN GENERAL MEDICINE AT %s", mc1.getName()),
				mc1.listAllDoctorsBySpeciality(GeneralMedicine.class));
		reporter.generateDoctorsReport(
				String.format("REPORT OF ALL GENERALISTIC DOCTORS IN Pediatricts AT %s", mc1.getName()),
				ms11.listAllGeneralisticDoctors());
		reporter.generateDoctorsReport(
				String.format("REPORT OF ALL GENERALISTIC DOCTORS IN General Medicine AT %s", mc1.getName()),
				ms12.listAllGeneralisticDoctors());

		db.findPatient("699999990")
			.ifPresent(reporter::generatePatientReport);

		reporter.generatePatientsReport(
				"REPORT OF ALL PATIENTS",
				db.listAllPatients());

		reporter.generatePatientsReport(
				"REPORT OF PATIENTS BY AGE RANGE 15-22",
				db.listAllPatientsByAgeRange(15, 22));
	}

}
