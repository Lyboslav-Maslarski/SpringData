package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentIdDTO {
    @XmlElement
    private Long id;

    public ApartmentIdDTO() {
    }

    public Long getId() {
        return id;
    }

    public ApartmentIdDTO setId(Long id) {
        this.id = id;
        return this;
    }
}
