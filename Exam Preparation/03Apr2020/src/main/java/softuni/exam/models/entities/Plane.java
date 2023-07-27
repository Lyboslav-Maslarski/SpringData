package softuni.exam.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "planes")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "register_number", unique = true)
    private String registerNumber;
    private Integer capacity;
    private String airline;

    public Plane() {
    }

    public long getId() {
        return id;
    }

    public Plane setId(long id) {
        this.id = id;
        return this;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public Plane setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
        return this;
    }
    public String getAirline() {
        return airline;
    }

    public Plane setAirline(String airline) {
        this.airline = airline;
        return this;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Plane setCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }
}
