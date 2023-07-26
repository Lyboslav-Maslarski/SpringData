package hiberspring.domain.dtos;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeImportDTO {
    @NotNull
    @XmlAttribute(name = "first-name")
    private String firstName;
    @NotNull
    @XmlAttribute(name = "last-name")
    private String lastName;
    @NotNull
    @XmlAttribute
    private String position;
    @NotNull
    @XmlElement
    private String card;
    @NotNull
    @XmlElement
    private String branch;

    public EmployeeImportDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public EmployeeImportDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public EmployeeImportDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public EmployeeImportDTO setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getCard() {
        return card;
    }

    public EmployeeImportDTO setCard(String card) {
        this.card = card;
        return this;
    }

    public String getBranch() {
        return branch;
    }

    public EmployeeImportDTO setBranch(String branch) {
        this.branch = branch;
        return this;
    }
}
