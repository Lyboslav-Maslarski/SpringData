package softuni.exam.models.dtos;

import javax.validation.constraints.Size;

public class PictureImportDTO {
    @Size(min = 2,max = 20)
    private String name;
    private String dateAndTime;
    private long car;

    public PictureImportDTO() {
    }

    public String getName() {
        return name;
    }

    public PictureImportDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public PictureImportDTO setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
        return this;
    }

    public long getCar() {
        return car;
    }

    public PictureImportDTO setCar(long car) {
        this.car = car;
        return this;
    }
}
