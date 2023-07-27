package softuni.exam.models.dtos;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CarImportDTO {
    @Size(min = 2,max = 20)
    private String make;
    @Size(min = 2,max = 20)
    private String model;
    @Positive
    private Integer kilometers;
    private String registeredOn;

    public CarImportDTO() {
    }

    public String getMake() {
        return make;
    }

    public CarImportDTO setMake(String make) {
        this.make = make;
        return this;
    }

    public String getModel() {
        return model;
    }

    public CarImportDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public CarImportDTO setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
        return this;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public CarImportDTO setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
        return this;
    }
}
