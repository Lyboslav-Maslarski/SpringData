package _04_HospitalDatabase.Entities;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    private String address;
    @Column(unique = true)
    private String email;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Lob
    private Blob picture;
    @Column(name = "insured", nullable = false)
    private boolean isInsured;
    @OneToMany(mappedBy = "patient", targetEntity = Visitation.class)
    private Set<Visitation> visitations;
    @ManyToMany
    @JoinTable(name = "patients_diagnoses",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"))
    private Set<Diagnose> diagnoses;
    @ManyToMany
    @JoinTable(name = "patients_medicaments",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"))
    private Set<Medicament> medicaments;

    public Patient() {
    }

    public Patient(String firstName, String lastName, LocalDate dateOfBirth, boolean isInsured) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.isInsured = isInsured;
        this.visitations = new HashSet<>();
        this.diagnoses = new HashSet<>();
        this.medicaments = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    public boolean isInsured() {
        return isInsured;
    }

    public void setInsured(boolean insured) {
        isInsured = insured;
    }

    public Set<Visitation> getVisitations() {
        return Collections.unmodifiableSet(visitations);
    }

    public void addVisitation(Visitation visitation) {
        this.visitations.add(visitation);
    }

    public Set<Diagnose> getDiagnoses() {
        return Collections.unmodifiableSet(diagnoses);
    }

    public void addDiagnose(Diagnose diagnose) {
        this.diagnoses.add(diagnose);
    }

    public Set<Medicament> getMedicaments() {
        return Collections.unmodifiableSet(medicaments);
    }

    public void addMedicament(Medicament medicament) {
        this.medicaments.add(medicament);
    }

    @Override
    public String toString() {
        return "Patient : " + getFirstName() + " " + getLastName() +
               System.lineSeparator() +
               "Insured : " + (isInsured() ? "yes" : "no") +
               System.lineSeparator() +
               "Address : " + (getAddress() == null ? "n/a" : getAddress()) +
               System.lineSeparator() +
               "E-mail : " + (getEmail() == null ? "n/a" : getEmail()) +
               System.lineSeparator() +
               "Date of birth : " + getDateOfBirth() +
               System.lineSeparator() +
               "Diagnose : " + diagnoses.stream().map(Diagnose::getName).collect(Collectors.joining(", ")) +
               System.lineSeparator() +
               "Medicaments : " + medicaments.stream().map(Medicament::getName).collect(Collectors.joining(", ")) +
               System.lineSeparator() +
               "Visitations : " + visitations.stream().map(v -> v.getDate().toString()).collect(Collectors.joining(", ")) +
               System.lineSeparator();
    }
}
