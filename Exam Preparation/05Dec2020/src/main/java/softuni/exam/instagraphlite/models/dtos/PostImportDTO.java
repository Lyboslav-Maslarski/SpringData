package softuni.exam.instagraphlite.models.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PostImportDTO {
    @NotNull
    @Size(min = 21)
    @XmlElement
    private String caption;
    @NotNull
    @XmlElement
    private UsernameDTO user;
    @NotNull
    @XmlElement
    private PicturePathDTO picture;

    public PostImportDTO() {
    }

    public String getCaption() {
        return caption;
    }

    public PostImportDTO setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public UsernameDTO getUser() {
        return user;
    }

    public PostImportDTO setUser(UsernameDTO user) {
        this.user = user;
        return this;
    }

    public PicturePathDTO getPicture() {
        return picture;
    }

    public PostImportDTO setPicture(PicturePathDTO picture) {
        this.picture = picture;
        return this;
    }
}
