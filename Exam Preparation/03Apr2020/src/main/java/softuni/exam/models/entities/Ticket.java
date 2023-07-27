package softuni.exam.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "serial_number",unique = true)
    private String serialNumber;
    private BigDecimal price;
    @Column(name = "take_off")
    private LocalDateTime takeoff;
    @OneToOne
    private Passenger passenger;
    @OneToOne
    private Plane plane;
    @OneToOne
    private Town fromTown;
    @OneToOne
    private Town toTown;

    public Ticket() {
    }

    public long getId() {
        return id;
    }

    public Ticket setId(long id) {
        this.id = id;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Ticket setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Ticket setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getTakeoff() {
        return takeoff;
    }

    public Ticket setTakeoff(LocalDateTime takeoff) {
        this.takeoff = takeoff;
        return this;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Ticket setPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public Plane getPlane() {
        return plane;
    }

    public Ticket setPlane(Plane plane) {
        this.plane = plane;
        return this;
    }

    public Town getFromTown() {
        return fromTown;
    }

    public Ticket setFromTown(Town fromTown) {
        this.fromTown = fromTown;
        return this;
    }

    public Town getToTown() {
        return toTown;
    }

    public Ticket setToTown(Town toTown) {
        this.toTown = toTown;
        return this;
    }
}
