package softuni.exam.models.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SellerImportDTO {
    @XmlElement(name = "first-name")
    @Size(min = 2, max = 20)
    private String firstName;
    @XmlElement(name = "last-name")
    @Size(min = 2, max = 20)
    private String lastName;
    @Email
    private String email;
    @NotNull
    private String rating;
    @NotNull
    private String town;

    public SellerImportDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public SellerImportDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public SellerImportDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SellerImportDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public SellerImportDTO setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getTown() {
        return town;
    }

    public SellerImportDTO setTown(String town) {
        this.town = town;
        return this;
    }
}
