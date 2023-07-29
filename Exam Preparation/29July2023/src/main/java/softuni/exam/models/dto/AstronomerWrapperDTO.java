package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerWrapperDTO {
    @XmlElement(name = "astronomer")
    private List<AstronomerImportDTO> astronomers;

    public AstronomerWrapperDTO() {
    }

    public List<AstronomerImportDTO> getAstronomers() {
        return astronomers;
    }

    public AstronomerWrapperDTO setAstronomers(List<AstronomerImportDTO> astronomers) {
        this.astronomers = astronomers;
        return this;
    }
}
