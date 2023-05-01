package _04_HospitalDatabase.core;

import _04_HospitalDatabase.Entities.Diagnose;
import _04_HospitalDatabase.Entities.Medicament;
import _04_HospitalDatabase.Entities.Patient;
import _04_HospitalDatabase.Entities.Visitation;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private final Scanner scanner;
    private final EntityManager entityManager;

    public ControllerImpl(Scanner scanner, EntityManager entityManager) {
        this.scanner = scanner;
        this.entityManager = entityManager;
    }

    @Override
    public String addPatient() {
        System.out.println("Please enter patient's first name.");
        String firstName = scanner.nextLine();
        System.out.println("Please enter patient's last name.");
        String lastName = scanner.nextLine();
        System.out.println("Please enter patient's date of birth.(date format YYYY-MM-DD)");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
        System.out.println("Please enter whether patient is insured.");
        boolean isInsured = scanner.nextLine().equalsIgnoreCase("y");

        Patient patient = new Patient(firstName, lastName, dateOfBirth, isInsured);
        entityManager.getTransaction().begin();
        entityManager.persist(patient);
        entityManager.getTransaction().commit();

        return String.format("Patient with first name:%s, last name:%s, date of birth:%s is added successfully.",
                firstName, lastName, dateOfBirth);
    }

    @Override
    public String addVisitation() {
        System.out.println("Please enter patient's first name.");
        String firstName = scanner.nextLine();
        System.out.println("Please enter patient's last name.");
        String lastName = scanner.nextLine();

        Patient patient = (Patient) entityManager.createNativeQuery("SELECT * FROM patients WHERE first_name = :firstName AND last_name = :lastName", Patient.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getSingleResult();

        System.out.println("Please enter diagnose.");
        String diagnoseName = scanner.nextLine();
        Diagnose diagnose = (Diagnose) entityManager.createNativeQuery("SELECT * FROM diagnoses WHERE name = :diagnoseName", Diagnose.class)
                .setParameter("diagnoseName", diagnoseName)
                .getSingleResult();

        System.out.println("Please enter medicament.");
        String medicamentName = scanner.nextLine();
        Medicament medicament = (Medicament) entityManager.createNativeQuery("SELECT * FROM medicaments WHERE name = :medicamentName", Medicament.class)
                .setParameter("medicamentName", medicamentName)
                .getSingleResult();

        Visitation visitation = new Visitation(LocalDate.now(), patient, diagnose);
        visitation.addMedicament(medicament);

        entityManager.getTransaction().begin();
        entityManager.persist(visitation);
        entityManager.getTransaction().commit();

        return String.format("On %s patient %s %s was visited, diagnosed with %s and prescribed %s.",
                visitation.getDate(), patient.getFirstName(), patient.getLastName(), diagnose.getName(), medicament.getName());
    }

    @Override
    public String addDiagnose() {
        System.out.println("Please enter diagnose name.");
        String diagnoseName = scanner.nextLine();
        Diagnose diagnose = new Diagnose(diagnoseName);

        entityManager.getTransaction().begin();
        entityManager.persist(diagnose);
        entityManager.getTransaction().commit();

        return String.format("Diagnose %s successfully added.", diagnoseName);
    }

    @Override
    public String addMedicament() {
        System.out.println("Please enter medicament name.");
        String medicamentName = scanner.nextLine();
        Medicament medicament = new Medicament(medicamentName);

        entityManager.getTransaction().begin();
        entityManager.persist(medicament);
        entityManager.getTransaction().commit();

        return String.format("Medicament %s successfully added.", medicamentName);
    }

    @Override
    public String findPatient() {
        System.out.println("Please enter patient's first name.");
        String firstName = scanner.nextLine();
        System.out.println("Please enter patient's last name.");
        String lastName = scanner.nextLine();
        entityManager.getTransaction().begin();
        Patient patient = (Patient) entityManager.createNativeQuery("SELECT * FROM patients WHERE first_name = :firstName AND last_name = :lastName", Patient.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getSingleResult();

        return patient.toString();
    }

    @Override
    public String findVisitationByDate() {
        System.out.println("Please enter visitation date.(date format YYYY-MM-DD)");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        List<Visitation> visitations = entityManager.createNativeQuery("SELECT * FROM visitations WHERE date = :searchedDate", Visitation.class)
                .setParameter("searchedDate", date)
                .getResultList();

        return visitations.stream().map(Visitation::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String closeProgram() {
        entityManager.close();
        return "Goodbye!";
    }
}
