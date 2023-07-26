package softuni.exam.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PicturesWrapperDTO {
    @XmlElement(name = "picture")
    private List<PictureImportDTO> pictures;

    public PicturesWrapperDTO() {
    }

    public List<PictureImportDTO> getPictures() {
        return pictures;
    }

    public PicturesWrapperDTO setPictures(List<PictureImportDTO> pictures) {
        this.pictures = pictures;
        return this;
    }
}
