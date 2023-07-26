package softuni.exam.domain.dtos;

import softuni.exam.domain.entities.Position;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PlayerImportDTO {
    @NotNull
    private String firstName;
    @Size(min = 3, max = 15)
    @NotNull
    private String lastName;
    @Min(1)
    @Max(99)
    @NotNull
    private Integer number;
    @Min(0)
    @NotNull
    private BigDecimal salary;
    @NotNull
    private String position;
    @NotNull
    private PictureImportDTO picture;
    @NotNull
    private TeamImportDTO team;

    public PlayerImportDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public PlayerImportDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PlayerImportDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public PlayerImportDTO setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public PlayerImportDTO setSalary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public PlayerImportDTO setPosition(String position) {
        this.position = position;
        return this;
    }

    public PictureImportDTO getPicture() {
        return picture;
    }

    public PlayerImportDTO setPicture(PictureImportDTO picture) {
        this.picture = picture;
        return this;
    }

    public TeamImportDTO getTeam() {
        return team;
    }

    public PlayerImportDTO setTeam(TeamImportDTO team) {
        this.team = team;
        return this;
    }
}
