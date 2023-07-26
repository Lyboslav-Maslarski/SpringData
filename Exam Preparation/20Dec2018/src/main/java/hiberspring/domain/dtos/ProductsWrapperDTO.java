package hiberspring.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsWrapperDTO {
    @XmlElement(name = "product")
    private List<ProductImportDTO> products;

    public ProductsWrapperDTO() {
    }

    public List<ProductImportDTO> getProducts() {
        return products;
    }

    public ProductsWrapperDTO setProducts(List<ProductImportDTO> products) {
        this.products = products;
        return this;
    }
}
