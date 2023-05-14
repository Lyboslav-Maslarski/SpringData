package CarDealer.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuppliersNoImporterDTO {
    @XmlElement(name = "suplier")
    private List<SupplierNoImporterDTO> list;

    public SuppliersNoImporterDTO(List<SupplierNoImporterDTO> list) {
        this.list = list;
    }

    public SuppliersNoImporterDTO() {
    }
}
