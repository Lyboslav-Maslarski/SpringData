package CarDealer.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsWithPartsDTO {
    @XmlElement(name = "car")
    private List<CarWithPartsDTO> list;

    public CarsWithPartsDTO(List<CarWithPartsDTO> list) {
        this.list = list;
    }

    public CarsWithPartsDTO() {
    }
}
