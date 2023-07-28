package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownImportDTO {
    @NotNull
    @Size(min = 2)
    private String townName;
    @NotNull
    @Positive
    private Integer population;

    public TownImportDTO() {
    }

    public String getTownName() {
        return townName;
    }

    public TownImportDTO setTownName(String townName) {
        this.townName = townName;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public TownImportDTO setPopulation(Integer population) {
        this.population = population;
        return this;
    }
}
