package _04_HospitalDatabase.Entities;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "diagnoses")
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String comments;
    @ManyToMany(mappedBy = "diagnoses", targetEntity = Patient.class)
    private Set<Patient> patients;

    public Diagnose() {
    }

    public Diagnose(String name) {
        this.name = name;
        this.patients = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Patient> getPatients() {
        return Collections.unmodifiableSet(patients);
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }
}
