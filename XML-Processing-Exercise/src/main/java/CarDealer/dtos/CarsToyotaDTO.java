package CarDealer.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsToyotaDTO {
    @XmlElement(name = "car")
    private List<CarToyotaDTO> list;

    public CarsToyotaDTO(List<CarToyotaDTO> list) {
        this.list = list;
    }

    public CarsToyotaDTO() {
    }

    public List<CarToyotaDTO> getList() {
        return list;
    }
}
