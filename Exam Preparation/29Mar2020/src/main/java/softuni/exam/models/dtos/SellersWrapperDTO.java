package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellersWrapperDTO {
    @XmlElement(name = "seller")
    private List<SellerImportDTO> sellers;

    public SellersWrapperDTO() {
    }

    public List<SellerImportDTO> getSellers() {
        return sellers;
    }

    public SellersWrapperDTO setSellers(List<SellerImportDTO> sellers) {
        this.sellers = sellers;
        return this;
    }
}
