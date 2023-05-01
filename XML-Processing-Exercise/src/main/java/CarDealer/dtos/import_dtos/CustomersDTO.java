package CarDealer.dtos.import_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersDTO {
    @XmlElement(name = "customer")
    private List<CustomerDTO> customers;

    public CustomersDTO() {
    }

    public List<CustomerDTO> getCustomers() {
        return customers;
    }
}
