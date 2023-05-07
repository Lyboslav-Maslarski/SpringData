package CarDealer.dtos.import_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartsDTO {
    @XmlElement(name = "part")
    private List<PartDTO> parts;

    public PartsDTO() {
    }

    public List<PartDTO> getParts() {
        return parts;
    }
}
