package softuni.exam.models.dtos;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneImportDTO {
    @Size(min = 5)
    @XmlElement
    private String registerNumber;
    @Positive
    @XmlElement
    private Integer capacity;
    @Size(min = 2)
    @XmlElement
    private String airline;

    public PlaneImportDTO() {
    }
}
