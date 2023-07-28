package softuni.exam.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentImportDTO {
    @NotNull
    @XmlElement
    private String apartmentType;
    @Min(40)
    @NotNull
    @XmlElement
    private Double area;
    @XmlElement
    private String town;

    public ApartmentImportDTO() {
    }

    public String getApartmentType() {
        return apartmentType;
    }

    public ApartmentImportDTO setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
        return this;
    }

    public Double getArea() {
        return area;
    }

    public ApartmentImportDTO setArea(Double area) {
        this.area = area;
        return this;
    }

    public String getTown() {
        return town;
    }

    public ApartmentImportDTO setTown(String town) {
        this.town = town;
        return this;
    }
}
