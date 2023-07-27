package softuni.exam.models.dtos;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
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
    @XmlElement(name = "name")
    @XmlElementWrapper(name = "from-town")
    private String fromTownName;
    @XmlElement(name = "name")
    @XmlElementWrapper(name = "to-town")
    private String toTownName;
    @XmlElement(name = "email")
    @XmlElementWrapper(name = "passenger")
    private String passengerEmail;
    @XmlElement(name = "register-number")
    @XmlElementWrapper(name = "plane")
    private String planeRegisterNumber;

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

    public String getFromTownName() {
        return fromTownName;
    }

    public TicketImportDTO setFromTownName(String fromTownName) {
        this.fromTownName = fromTownName;
        return this;
    }

    public String getToTownName() {
        return toTownName;
    }

    public TicketImportDTO setToTownName(String toTownName) {
        this.toTownName = toTownName;
        return this;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public TicketImportDTO setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
        return this;
    }

    public String getPlaneRegisterNumber() {
        return planeRegisterNumber;
    }

    public TicketImportDTO setPlaneRegisterNumber(String planeRegisterNumber) {
        this.planeRegisterNumber = planeRegisterNumber;
        return this;
    }
}
