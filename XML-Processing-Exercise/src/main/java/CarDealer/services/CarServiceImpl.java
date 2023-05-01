package CarDealer.services;

import CarDealer.dtos.CarToyotaDTO;
import CarDealer.dtos.CarWithPartsDTO;
import CarDealer.dtos.CarsToyotaDTO;
import CarDealer.dtos.CarsWithPartsDTO;
import CarDealer.repositories.CarRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public CarsToyotaDTO getAllFromToyota(String maker) {
        List<CarToyotaDTO> list = carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(maker).stream()
                .map(car -> modelMapper.map(car, CarToyotaDTO.class))
                .collect(Collectors.toList());

        return new CarsToyotaDTO(list);
    }

    @Override
    @Transactional
    public CarsWithPartsDTO getAllWithParts() {
        List<CarWithPartsDTO> list = carRepository.findAll().stream()
                .map(car -> modelMapper.map(car, CarWithPartsDTO.class))
                .collect(Collectors.toList());
        return new CarsWithPartsDTO(list);
    }
}
