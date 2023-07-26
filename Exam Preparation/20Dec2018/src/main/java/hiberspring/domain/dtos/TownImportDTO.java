package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class TownImportDTO {
    @NotNull
    @Expose
    private String name;
    @NotNull
    @Expose
    private Integer population;

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
}
