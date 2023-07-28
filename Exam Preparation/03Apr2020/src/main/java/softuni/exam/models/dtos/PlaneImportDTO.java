package softuni.exam.models.dtos;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneImportDTO {
    @Size(min = 5)
    @XmlElement(name = "register-number")
    private String registerNumber;
    @Positive
    @XmlElement
    private Integer capacity;
    @Size(min = 2)
    @XmlElement
    private String airline;

    public PlaneImportDTO() {
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public PlaneImportDTO setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
        return this;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public PlaneImportDTO setCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public String getAirline() {
        return airline;
    }

    public PlaneImportDTO setAirline(String airline) {
        this.airline = airline;
        return this;
    }
}
