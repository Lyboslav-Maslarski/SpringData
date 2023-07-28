package softuni.exam.models.dto;

import javax.validation.constraints.*;

public class PartImportDTO {
    @NotNull
    @Size(min = 2,max = 19)
    private String partName;
    @Min(10)
    @Max(2000)
    @NotNull
    private Double price;
    @NotNull
    @Positive
    private Integer quantity;

    public PartImportDTO() {
    }

    public String getPartName() {
        return partName;
    }

    public PartImportDTO setPartName(String partName) {
        this.partName = partName;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public PartImportDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PartImportDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
