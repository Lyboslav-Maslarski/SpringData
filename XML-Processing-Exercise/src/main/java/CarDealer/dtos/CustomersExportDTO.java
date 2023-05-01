package CarDealer.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersExportDTO {

    @XmlElement(name = "customer")
    private List<CustomerExportDTO> list;

    public CustomersExportDTO(List<CustomerExportDTO> list) {
        this.list = list;
    }

    public CustomersExportDTO() {
    }

    public List<CustomerExportDTO> getList() {
        return list;
    }
}
