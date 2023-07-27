package softuni.exam.models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private LocalDateTime dateAndTime;
    @ManyToOne
    private Car car;

    public Picture() {
    }

    public long getId() {
        return id;
    }

    public Picture setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Picture setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public Picture setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
        return this;
    }

    public Car getCar() {
        return car;
    }

    public Picture setCar(Car car) {
        this.car = car;
        return this;
    }
}
