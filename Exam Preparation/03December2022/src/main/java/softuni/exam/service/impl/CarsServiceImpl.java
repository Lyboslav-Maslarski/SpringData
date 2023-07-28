package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarImportDTO;
import softuni.exam.models.dto.CarsWrapperDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarsServiceImpl implements CarsService {
    public static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";
    private final CarsRepository carsRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarsServiceImpl(CarsRepository carsRepository, XmlParser xmlParser,
                           ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carsRepository = carsRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return carsRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws JAXBException {
        CarsWrapperDTO carsWrapperDTO = xmlParser.parseXml(CarsWrapperDTO.class, CARS_FILE_PATH);

        return carsWrapperDTO.getCars()
                .stream()
                .map(this::importCar)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importCar(CarImportDTO carImportDTO) {
        if (!validationUtil.isValid(carImportDTO)) {
            return "Invalid car";
        }

        Optional<Car> existingCar = carsRepository.findByPlateNumber(carImportDTO.getPlateNumber());
        if (existingCar.isPresent()) {
            return "Invalid car";
        }

        Car car = modelMapper.map(carImportDTO, Car.class);
        carsRepository.save(car);

        return String.format("Successfully imported car %s - %s", car.getCarMake(), car.getCarModel());
    }
}
