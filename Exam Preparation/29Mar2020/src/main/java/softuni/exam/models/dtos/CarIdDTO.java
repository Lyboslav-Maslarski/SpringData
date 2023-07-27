package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CarIdDTO {
    @XmlElement
    private long id;

    public CarIdDTO() {
    }

    public long getId() {
        return id;
    }

    public CarIdDTO setId(long id) {
        this.id = id;
        return this;
    }
}
