package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FirstNameDTO {
    @XmlElement
    private String firstName;

    public FirstNameDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public FirstNameDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
}
