package car_dealer.services;

import car_dealer.dtos.export_dtos.CarExportDTO;
import car_dealer.dtos.export_dtos.CarToyotaDTO;
import car_dealer.dtos.export_dtos.CarWithPartsDTO;
import car_dealer.repositories.CarRepository;
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
    public List<CarToyotaDTO> getAllFromToyota(String maker) {
        return carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(maker).stream()
                .map(car -> modelMapper.map(car, CarToyotaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CarWithPartsDTO> getAllWithParts() {
        return carRepository.findAll().stream()
                .map(car -> modelMapper.map(car, CarWithPartsDTO.class))
                .collect(Collectors.toList());
    }
}
