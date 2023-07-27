package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class PassengerImportDTO {
    @Expose
    @Size(min = 2)
    private String firstName;
    @Expose
    @Size(min = 2)
    private String lastName;
    @Expose
    @Positive
    private Integer age;
    @Expose
    private String phoneNumber;
    @Expose
    @Email
    private String email;
    @Expose
    private String town;

    public PassengerImportDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public PassengerImportDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PassengerImportDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public PassengerImportDTO setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PassengerImportDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PassengerImportDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTown() {
        return town;
    }

    public PassengerImportDTO setTown(String town) {
        this.town = town;
        return this;
    }
}
