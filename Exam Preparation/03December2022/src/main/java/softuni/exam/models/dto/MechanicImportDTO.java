package softuni.exam.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MechanicImportDTO {
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 2)
    private String firstName;
    @NotNull
    @Size(min = 2)
    private String lastName;
    @Size(min = 2)
    private String phone;

    public MechanicImportDTO() {
    }

    public String getEmail() {
        return email;
    }

    public MechanicImportDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public MechanicImportDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public MechanicImportDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public MechanicImportDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
