package CarDealer.dtos.import_dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuppliersDTO {

    @XmlElement(name = "supplier")
    private List<SupplierDTO> suppliers;

    public SuppliersDTO() {
    }

    public List<SupplierDTO> getSuppliers() {
        return suppliers;
    }
}
