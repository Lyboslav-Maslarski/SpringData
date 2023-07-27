package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SellerIdDTO {
    @XmlElement
    private long id;

    public SellerIdDTO() {
    }

    public long getId() {
        return id;
    }

    public SellerIdDTO setId(long id) {
        this.id = id;
        return this;
    }
}
