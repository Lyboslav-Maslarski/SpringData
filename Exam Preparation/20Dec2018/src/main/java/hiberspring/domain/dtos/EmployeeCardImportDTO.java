package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class EmployeeCardImportDTO {
    @NotNull
    @Expose
    private String number;

    public EmployeeCardImportDTO() {
    }

    public String getNumber() {
        return number;
    }

    public EmployeeCardImportDTO setNumber(String number) {
        this.number = number;
        return this;
    }
}
