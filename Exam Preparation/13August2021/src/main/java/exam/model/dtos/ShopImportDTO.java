package exam.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportDTO {
    @XmlElement
    private String address;
    @XmlElement(name = "employee-count")
    private int employeeCount;
    @XmlElement
    private BigDecimal income;
    @XmlElement
    private String name;
    @XmlElement(name = "shop-area")
    private int shopArea;
    @XmlElement
    private NameDTO town;

    public String getAddress() {
        return address;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public String getName() {
        return name;
    }

    public int getShopArea() {
        return shopArea;
    }

    public NameDTO getTown() {
        return town;
    }

    public boolean isValid() {
        if (this.name.length() < 4) {
            return false;
        }
        if (this.income.compareTo(BigDecimal.valueOf(20000)) < 0) {
            return false;
        }
        if (this.address.length() < 4) {
            return false;
        }
        if (this.employeeCount < 1 || this.employeeCount > 50) {
            return false;
        }
        if (this.shopArea < 150) {
            return false;
        }
        return true;
    }
}
