package _04_HospitalDatabase.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "visitations")
public class Visitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate date;

    private String comments;

    @ManyToOne(optional = false, targetEntity = Patient.class)
    private Patient patient;

    @OneToOne(optional = false, targetEntity = Diagnose.class)
    @JoinColumn(name = "diagnose_id")
    private Diagnose diagnose;

    @ManyToMany
    @JoinTable(name = "visitations_medicaments",
            joinColumns = @JoinColumn(name = "visitation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"))
    private Set<Medicament> medicaments;

    public Visitation() {
    }

    public Visitation(LocalDate date, Patient patient, Diagnose diagnose) {
        this.date = date;
        this.patient = patient;
        this.diagnose = diagnose;
        this.medicaments = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    public Set<Medicament> getMedicaments() {
        return Collections.unmodifiableSet(medicaments);
    }

    public void addMedicament(Medicament medicament) {
        this.medicaments.add(medicament);
    }

    @Override
    public String toString() {
        return "Visitation : " + id + " on " + date + System.lineSeparator() +
               "Patient : " + patient.getFirstName() + " " + patient.getLastName() + System.lineSeparator() +
               "Diagnose : " + diagnose.getName() + System.lineSeparator() +
               "Medicaments : " + medicaments.stream().map(Medicament::getName).collect(Collectors.joining(", "));
    }
}