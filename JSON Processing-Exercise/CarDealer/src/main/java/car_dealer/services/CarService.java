package car_dealer.services;

import car_dealer.dtos.export_dtos.CarToyotaDTO;
import car_dealer.dtos.export_dtos.CarWithPartsDTO;

import java.util.List;

public interface CarService {
    List<CarToyotaDTO> getAllFromToyota(String maker);

    List<CarWithPartsDTO> getAllWithParts();
}
