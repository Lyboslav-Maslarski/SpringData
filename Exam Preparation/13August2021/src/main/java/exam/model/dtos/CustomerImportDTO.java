package exam.model.dtos;

import java.util.Date;

public class CustomerImportDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Date registeredOn;
    private NameDTO town;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public NameDTO getTown() {
        return town;
    }

    public boolean isValid() {
        if (firstName.length() < 2) {
            return false;
        }
        if (lastName.length() < 2) {
            return false;
        }
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        return true;
    }
}
