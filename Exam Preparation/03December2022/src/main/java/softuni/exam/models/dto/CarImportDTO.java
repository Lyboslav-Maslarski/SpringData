package softuni.exam.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportDTO {
    @NotNull
    @XmlElement
    private String carType;
    @NotNull
    @Size(min = 2,max = 30)
    @XmlElement
    private String carMake;
    @NotNull
    @Size(min = 2,max = 30)
    @XmlElement
    private String carModel;
    @NotNull
    @Positive
    @XmlElement
    private Integer year;
    @NotNull
    @Size(min = 2,max = 30)
    @XmlElement
    private String plateNumber;
    @NotNull
    @Positive
    @XmlElement
    private Integer kilometers;
    @NotNull
    @Min(1)
    @XmlElement
    private Double engine;

    public CarImportDTO() {
    }

    public String getCarType() {
        return carType;
    }

    public CarImportDTO setCarType(String carType) {
        this.carType = carType;
        return this;
    }

    public String getCarMake() {
        return carMake;
    }

    public CarImportDTO setCarMake(String carMake) {
        this.carMake = carMake;
        return this;
    }

    public String getCarModel() {
        return carModel;
    }

    public CarImportDTO setCarModel(String carModel) {
        this.carModel = carModel;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public CarImportDTO setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public CarImportDTO setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public CarImportDTO setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
        return this;
    }

    public Double getEngine() {
        return engine;
    }

    public CarImportDTO setEngine(Double engine) {
        this.engine = engine;
        return this;
    }
}
