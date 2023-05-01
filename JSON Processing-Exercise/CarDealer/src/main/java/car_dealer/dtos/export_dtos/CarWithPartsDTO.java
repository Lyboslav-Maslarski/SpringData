package car_dealer.dtos.export_dtos;

import java.util.List;

public class CarWithPartsDTO {
    private CarExportDTO car;
    private List<PartExportDTO> parts;

    public CarExportDTO getCar() {
        return car;
    }

    public void setCar(CarExportDTO car) {
        this.car = car;
    }

    public List<PartExportDTO> getParts() {
        return parts;
    }

    public void setParts(List<PartExportDTO> parts) {
        this.parts = parts;
    }
}
