package exam.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopWrapperDTO {
    @XmlElement(name = "shop")
    private List<ShopImportDTO> list;

    public ShopWrapperDTO(List<ShopImportDTO> list) {
        this.list = list;
    }

    public ShopWrapperDTO() {
    }

    public List<ShopImportDTO> getList() {
        return list;
    }
}
