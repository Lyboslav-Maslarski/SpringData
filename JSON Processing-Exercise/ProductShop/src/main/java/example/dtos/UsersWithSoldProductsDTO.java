package example.dtos;

import java.util.List;

public class UsersWithSoldProductsDTO {
    private int usersCount;
    private List<UserWithSoldProductsExtendedDTO> users;

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserWithSoldProductsExtendedDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithSoldProductsExtendedDTO> users) {
        this.users = users;
    }
}
