package softuni.exam.models.dto;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerImportDTO {
    @NotNull
    @Min(500)
    @XmlElement(name = "average_observation_hours")
    private Double averageObservationHours;
    @XmlElement
    private String birthday;
    @NotNull
    @XmlElement(name = "first_name")
    @Size(min = 2,max = 30)
    private String firstName;
    @NotNull
    @Size(min = 2,max = 30)
    @XmlElement(name = "last_name")
    private String lastName;
    @NotNull
    @Min(15000)
    @XmlElement
    private Double salary;
    @NotNull
    @XmlElement(name = "observing_star_id")
    private Long observingStarId;

    public AstronomerImportDTO() {
    }

    public Double getAverageObservationHours() {
        return averageObservationHours;
    }

    public AstronomerImportDTO setAverageObservationHours(Double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public AstronomerImportDTO setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AstronomerImportDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AstronomerImportDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Double getSalary() {
        return salary;
    }

    public AstronomerImportDTO setSalary(Double salary) {
        this.salary = salary;
        return this;
    }

    public Long getObservingStarId() {
        return observingStarId;
    }

    public AstronomerImportDTO setObservingStarId(Long observingStarId) {
        this.observingStarId = observingStarId;
        return this;
    }
}
