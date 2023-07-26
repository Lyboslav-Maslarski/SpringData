package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class BranchImportDTO {
    @NotNull
    @Expose
    private String name;

    @NotNull
    @Expose
    private String town;

    public BranchImportDTO() {
    }

    public String getName() {
        return name;
    }

    public BranchImportDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getTown() {
        return town;
    }

    public BranchImportDTO setTown(String town) {
        this.town = town;
        return this;
    }
}
