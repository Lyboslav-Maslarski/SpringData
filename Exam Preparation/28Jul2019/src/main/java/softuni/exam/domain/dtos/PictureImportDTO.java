package softuni.exam.domain.dtos;


import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "picture")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureImportDTO {
    @XmlElement
    @NotNull
    @Expose
    private String url;

    public PictureImportDTO() {
    }

    public String getUrl() {
        return url;
    }

    public PictureImportDTO setUrl(String url) {
        this.url = url;
        return this;
    }
}
