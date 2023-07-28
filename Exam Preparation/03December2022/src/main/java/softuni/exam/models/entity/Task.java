package softuni.exam.models.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "parts_id")
    private Part part;
    @ManyToOne
    private Mechanic mechanic;
    @ManyToOne
    @JoinColumn(name = "cars_id")
    private Car car;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public Task setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Task setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Task setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Part getPart() {
        return part;
    }

    public Task setPart(Part part) {
        this.part = part;
        return this;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public Task setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
        return this;
    }

    public Car getCar() {
        return car;
    }

    public Task setCar(Car car) {
        this.car = car;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Car %s %s with %dkm\n" +
                        "-Mechanic: %s %s - task №%d:\n" +
                        " --Engine: %s%n" +
                        "---Price: %s$",
                getCar().getCarMake(), getCar().getCarModel(), getCar().getKilometers(),
                getMechanic().getFirstName(), getMechanic().getLastName(), getId(),
                getCar().getEngine(),
                getPrice());
    }
}
