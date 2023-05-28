package ProductShop.exportDTOS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldItemDTO {

    @XmlElement(name = "user")
    private List<UserWithSoldItemDTO> users;

    public UsersWithSoldItemDTO() {
    }

    public UsersWithSoldItemDTO(List<UserWithSoldItemDTO> users) {
        this.users = users;
    }
}
