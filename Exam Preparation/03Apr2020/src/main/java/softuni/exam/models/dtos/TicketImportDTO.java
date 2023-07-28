package softuni.exam.models.dtos;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class TicketImportDTO {
    @XmlElement(name = "serial-number")
    @Size(min = 2)
    private String serialNumber;
    @XmlElement
    @Positive
    private BigDecimal price;
    @XmlElement(name = "take-off")
    private String takeoff;
    @XmlElement(name = "from-town")
    private TownNameImportDTO fromTown;
    @XmlElement(name = "to-town")
    private TownNameImportDTO toTown;
    @XmlElement
    private PassengerEmailImportDTO passenger;
    @XmlElement
    private PlaneRegisterNumberImportDTO plane;

    public TicketImportDTO() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public TicketImportDTO setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TicketImportDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getTakeoff() {
        return takeoff;
    }

    public TicketImportDTO setTakeoff(String takeoff) {
        this.takeoff = takeoff;
        return this;
    }

    public TownNameImportDTO getFromTown() {
        return fromTown;
    }

    public TicketImportDTO setFromTown(TownNameImportDTO fromTown) {
        this.fromTown = fromTown;
        return this;
    }

    public TownNameImportDTO getToTown() {
        return toTown;
    }

    public TicketImportDTO setToTown(TownNameImportDTO toTown) {
        this.toTown = toTown;
        return this;
    }

    public PassengerEmailImportDTO getPassenger() {
        return passenger;
    }

    public TicketImportDTO setPassenger(PassengerEmailImportDTO passenger) {
        this.passenger = passenger;
        return this;
    }

    public PlaneRegisterNumberImportDTO getPlane() {
        return plane;
    }

    public TicketImportDTO setPlane(PlaneRegisterNumberImportDTO plane) {
        this.plane = plane;
        return this;
    }
}
