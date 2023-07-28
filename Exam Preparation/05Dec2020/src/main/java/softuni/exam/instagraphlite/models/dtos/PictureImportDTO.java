package softuni.exam.instagraphlite.models.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PictureImportDTO {
    @NotNull
    private String path;
    @Min(500)
    @Max(60000)
    @NotNull
    private Double size;

    public PictureImportDTO() {
    }

    public String getPath() {
        return path;
    }

    public PictureImportDTO setPath(String path) {
        this.path = path;
        return this;
    }

    public Double getSize() {
        return size;
    }

    public PictureImportDTO setSize(Double size) {
        this.size = size;
        return this;
    }
}
