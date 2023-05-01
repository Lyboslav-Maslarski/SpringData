package CarDealer.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersWithSalesDTO {
    @XmlElement(name = "customer")
    private List<CustomerWithSalesDTO> list;

    public CustomersWithSalesDTO(List<CustomerWithSalesDTO> list) {
        this.list = list;
    }

    public CustomersWithSalesDTO() {
    }
}
