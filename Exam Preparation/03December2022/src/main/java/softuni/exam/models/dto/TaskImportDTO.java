package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TaskImportDTO {
    @NotNull
    @Positive
    @XmlElement
    private Double price;
    @NotNull
    @XmlElement
    private String date;
    @NotNull
    @XmlElement
    private IdDTO car;
    @NotNull
    @XmlElement
    private FirstNameDTO mechanic;
    @NotNull
    @XmlElement
    private IdDTO part;

    public TaskImportDTO() {
    }

    public Double getPrice() {
        return price;
    }

    public TaskImportDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getDate() {
        return date;
    }

    public TaskImportDTO setDate(String date) {
        this.date = date;
        return this;
    }

    public IdDTO getCar() {
        return car;
    }

    public TaskImportDTO setCar(IdDTO car) {
        this.car = car;
        return this;
    }

    public FirstNameDTO getMechanic() {
        return mechanic;
    }

    public TaskImportDTO setMechanic(FirstNameDTO mechanic) {
        this.mechanic = mechanic;
        return this;
    }

    public IdDTO getPart() {
        return part;
    }

    public TaskImportDTO setPart(IdDTO part) {
        this.part = part;
        return this;
    }
}
