package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PictureImportDTO;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, CarRepository carRepository,
                              Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/pictures.json"));
    }

    @Override
    public String importPictures() throws IOException {
        PictureImportDTO[] pictureImportDTOS = gson.fromJson(readPicturesFromFile(), PictureImportDTO[].class);

        return Arrays.stream(pictureImportDTOS)
                .map(this::importPicture)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPicture(PictureImportDTO pictureImportDTO) {
        if (!validationUtil.isValid(pictureImportDTO)) {
            return "Invalid picture";
        }

        Optional<Picture> existingPicture = pictureRepository
                .findByName(pictureImportDTO.getName());
        if (existingPicture.isPresent()) {
            return "Invalid picture";
        }

        Optional<Car> car = carRepository
                .findById(pictureImportDTO.getCar());
        if (car.isEmpty()) {
            return "Invalid picture";
        }

        Picture picture = modelMapper.map(pictureImportDTO, Picture.class);
        picture.setCar(car.get());
        picture.setDateAndTime(LocalDateTime.parse(pictureImportDTO.getDateAndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        pictureRepository.save(picture);

        return String.format("Successfully imported picture - %s", picture.getName());
    }
}
