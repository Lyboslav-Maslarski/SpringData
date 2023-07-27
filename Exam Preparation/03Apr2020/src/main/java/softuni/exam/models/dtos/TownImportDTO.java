package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownImportDTO {
    @Expose
    @Size(min = 2)
    private String name;
    @Expose
    @Positive
    private Integer population;
    @Expose
    private String guide;

    public TownImportDTO() {
    }

    public String getName() {
        return name;
    }

    public TownImportDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public TownImportDTO setPopulation(Integer population) {
        this.population = population;
        return this;
    }

    public String getGuide() {
        return guide;
    }

    public TownImportDTO setGuide(String guide) {
        this.guide = guide;
        return this;
    }
}
