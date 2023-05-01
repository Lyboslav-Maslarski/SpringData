package ProductShop.exportDTOS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesExportDTO {
    @XmlElement(name = "category")
    private List<CategoryExportDTO> categories;

    public CategoriesExportDTO() {
    }

    public CategoriesExportDTO(List<CategoryExportDTO> categories) {
        this.categories = categories;
    }

    public List<CategoryExportDTO> getCategories() {
        return categories;
    }
}
