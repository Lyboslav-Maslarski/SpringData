package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.CarExportDTO;
import softuni.exam.models.dtos.CarImportDTO;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/cars.json"));
    }

    @Override
    public String importCars() throws IOException {
        CarImportDTO[] carImportDTOS = gson.fromJson(readCarsFileContent(), CarImportDTO[].class);

        return Arrays.stream(carImportDTOS)
                .map(this::importCar)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importCar(CarImportDTO carImportDTO) {
        if (!validationUtil.isValid(carImportDTO)) {
            return "Invalid car";
        }

        Optional<Car> existingCar = carRepository
                .findByMakeAndModelAndKilometers(carImportDTO.getMake(), carImportDTO.getModel(), carImportDTO.getKilometers());
        if (existingCar.isPresent()) {
            return "Invalid car";
        }

        Car car = modelMapper.map(carImportDTO, Car.class);
        car.setRegisteredOn(LocalDate.parse(carImportDTO.getRegisteredOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        carRepository.save(car);

        return String.format("Successfully imported car - %s - %s", car.getMake(), car.getModel());
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        List<CarExportDTO> allCars = carRepository.findAllCars();
        return allCars.stream()
                .map(CarExportDTO::print)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
