package softuni.exam.instagraphlite.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class UsernameDTO {
    @XmlElement
    private String username;

    public UsernameDTO() {
    }

    public String getUsername() {
        return username;
    }

    public UsernameDTO setUsername(String username) {
        this.username = username;
        return this;
    }
}
