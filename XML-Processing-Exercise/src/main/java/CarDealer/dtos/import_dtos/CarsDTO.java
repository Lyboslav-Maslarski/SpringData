package CarDealer.dtos.import_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsDTO {
    @XmlElement(name = "car")
    private List<CarDTO> cars;

    public CarsDTO() {
    }

    public List<CarDTO> getCars() {
        return cars;
    }
}
