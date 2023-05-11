package CarDealer.dtos.export_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesDTO {
    @XmlElement(name = "sale")
    private List<SaleDTO> list;

    public SalesDTO(List<SaleDTO> list) {
        this.list = list;
    }

    public SalesDTO() {
    }
}
