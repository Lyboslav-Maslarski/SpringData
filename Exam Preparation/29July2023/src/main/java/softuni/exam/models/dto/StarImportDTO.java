package softuni.exam.models.dto;

import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.StarType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class StarImportDTO {
    @NotNull
    @Size(min = 2, max = 30)
    private String name;
    @NotNull
    @Positive
    private Double lightYears;
    @NotNull
    @Size(min = 6)
    private String description;
    @NotNull
    private String starType;
    @NotNull
    private Long constellation;

    public StarImportDTO() {
    }

    public String getName() {
        return name;
    }

    public StarImportDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public StarImportDTO setLightYears(Double lightYears) {
        this.lightYears = lightYears;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StarImportDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStarType() {
        return starType;
    }

    public StarImportDTO setStarType(String starType) {
        this.starType = starType;
        return this;
    }

    public Long getConstellation() {
        return constellation;
    }

    public StarImportDTO setConstellation(Long constellation) {
        this.constellation = constellation;
        return this;
    }
}
