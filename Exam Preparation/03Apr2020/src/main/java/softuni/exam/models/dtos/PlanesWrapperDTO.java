package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanesWrapperDTO {
    @XmlElement(name = "plane")
    private List<PlaneImportDTO> planes;

    public PlanesWrapperDTO() {
    }

    public List<PlaneImportDTO> getPlanes() {
        return planes;
    }

    public PlanesWrapperDTO setPlanes(List<PlaneImportDTO> planes) {
        this.planes = planes;
        return this;
    }
}
