package softuni.exam.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AgentImportDTO {
    @NotNull
    @Size(min = 2)
    private String firstName;
    @NotNull
    @Size(min = 2)
    private String lastName;
    @NotNull
    private String town;
    @NotNull
    @Email
    private String email;

    public AgentImportDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public AgentImportDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AgentImportDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getTown() {
        return town;
    }

    public AgentImportDTO setTown(String town) {
        this.town = town;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AgentImportDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
