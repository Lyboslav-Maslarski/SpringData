package ProductShop.exportDTOS;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldItemAndCountDTO {
    @XmlElement(name = "user")
    private List<UserWithSoldItemAndCountDTO> userWithSoldItemAndCountDTOS;
    @XmlAttribute(name = "count")
    private int countUsers;

    public UsersWithSoldItemAndCountDTO() {
    }

    public UsersWithSoldItemAndCountDTO(List<UserWithSoldItemAndCountDTO> dtos) {
        this.userWithSoldItemAndCountDTOS = dtos;
        this.countUsers = dtos.size();
    }

    public List<UserWithSoldItemAndCountDTO> getUserWithSoldItemAndCountDTOS() {
        return userWithSoldItemAndCountDTOS;
    }

    public void setUserWithSoldItemAndCountDTOS(List<UserWithSoldItemAndCountDTO> userWithSoldItemAndCountDTOS) {
        this.userWithSoldItemAndCountDTOS = userWithSoldItemAndCountDTOS;
    }

    public int getCountUsers() {
        return countUsers;
    }

    public void setCountUsers(int countUsers) {
        this.countUsers = countUsers;
    }
}
