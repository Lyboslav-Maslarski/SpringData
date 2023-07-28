package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TownNameImportDTO {
    @XmlElement
    private String name;

    public TownNameImportDTO() {
    }

    public String getName() {
        return name;
    }

    public TownNameImportDTO setName(String name) {
        this.name = name;
        return this;
    }
}
