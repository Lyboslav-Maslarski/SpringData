package ProductShop.exportDTOS;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsDTO {
    @XmlAttribute
    private int count;
    @XmlElement(name = "product")
    private List<ProductExportDTO> products;

    public SoldProductsDTO(List<ProductExportDTO> products) {
        this.count = products.size();
        this.products = products;
    }

    public SoldProductsDTO() {
    }
}
