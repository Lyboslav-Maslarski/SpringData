package ProductShop.exportDTOS;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldItemAndCountDTO {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute
    private int age;
    @XmlElement(name = "sold-products")
    private SoldProductsDTO soldProducts;

    public UserWithSoldItemAndCountDTO() {
    }

    public void setSoldProducts(SoldProductsDTO soldProducts) {
        this.soldProducts = soldProducts;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
