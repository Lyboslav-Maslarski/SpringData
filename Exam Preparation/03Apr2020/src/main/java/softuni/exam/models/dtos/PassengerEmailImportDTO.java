package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PassengerEmailImportDTO {
    @XmlElement
    private String email;

    PassengerEmailImportDTO() {
    }

    public String getEmail() {
        return email;
    }

    public PassengerEmailImportDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
