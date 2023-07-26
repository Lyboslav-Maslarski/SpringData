package softuni.exam.domain.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamImportDTO {
    @XmlElement
    @Size(min = 3, max = 20)
    @NotNull
    private String name;
    @XmlElement
    @NotNull
    private PictureImportDTO picture;

    public TeamImportDTO() {
    }

    public String getName() {
        return name;
    }

    public TeamImportDTO setName(String name) {
        this.name = name;
        return this;
    }

    public PictureImportDTO getPicture() {
        return picture;
    }

    public TeamImportDTO setPicture(PictureImportDTO picture) {
        this.picture = picture;
        return this;
    }
}
