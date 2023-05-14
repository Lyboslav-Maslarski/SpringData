package exam.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownWrapperDTO {
    @XmlElement(name = "town")
    private List<TownImportDTO> list;

    public TownWrapperDTO(List<TownImportDTO> list) {
        this.list = list;
    }

    public TownWrapperDTO() {
    }

    public List<TownImportDTO> getList() {
        return list;
    }
}
