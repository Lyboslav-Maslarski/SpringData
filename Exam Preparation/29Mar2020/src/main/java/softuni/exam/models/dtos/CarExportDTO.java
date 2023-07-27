package softuni.exam.models.dtos;

import java.time.LocalDate;

public interface CarExportDTO {
    String getMake();

    public String getModel();

    public int getKilometers();
    public LocalDate getRegistered_on();

    public long getPicturesCount();

    default String print() {
        return String.format("Car make - %s, model - %s%n" +
                "      Kilometers - %d%n" +
                "      Registered on - %s%n" +
                "      Number of pictures - %d", getMake(), getModel(), getKilometers(), getRegistered_on(), getPicturesCount());
    }
}
