package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class IdDTO {
    @XmlElement
    private Long id;

    public IdDTO() {
    }

    public Long getId() {
        return id;
    }

    public IdDTO setId(Long id) {
        this.id = id;
        return this;
    }
}
