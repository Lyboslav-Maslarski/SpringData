package CarDealer.services;

import CarDealer.dtos.CarsToyotaDTO;
import CarDealer.dtos.CarsWithPartsDTO;

public interface CarService {
    CarsToyotaDTO getAllFromToyota(String maker);

    CarsWithPartsDTO getAllWithParts();
}
