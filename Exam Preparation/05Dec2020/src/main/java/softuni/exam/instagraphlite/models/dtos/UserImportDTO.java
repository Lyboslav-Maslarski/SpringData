package softuni.exam.instagraphlite.models.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserImportDTO {
    @NotNull
    @Size(min = 2,max = 18)
    private String username;
    @NotNull
    @Size(min = 4)
    private String password;
    @NotNull
    private String profilePicture;

    public UserImportDTO() {
    }

    public String getUsername() {
        return username;
    }

    public UserImportDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserImportDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public UserImportDTO setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }
}
