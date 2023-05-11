package org.example.models.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class RegistrationDTO {
    @Size(min = 3, message = "Username must be at least 3 symbols long.")
    private String username;
    @Size(min = 3)
    private String password;
    private String confirmPassword;
    @Email
    private String email;

    public RegistrationDTO(String username, String password, String confirmPassword, String email) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }

    public RegistrationDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "RegistrationDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
