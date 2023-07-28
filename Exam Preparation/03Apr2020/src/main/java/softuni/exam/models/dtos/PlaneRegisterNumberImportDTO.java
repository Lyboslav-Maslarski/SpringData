package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneRegisterNumberImportDTO {
    @XmlElement(name = "register-number")
    private String registerNumber;

    public PlaneRegisterNumberImportDTO() {
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public PlaneRegisterNumberImportDTO setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
        return this;
    }
}
