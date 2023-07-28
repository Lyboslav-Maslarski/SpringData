package softuni.exam.instagraphlite.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.PictureImportDTO;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper,
                              Gson gson, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/pictures.json"));
    }

    @Override
    public String importPictures() throws IOException {
        PictureImportDTO[] pictureImportDTOS = gson.fromJson(readFromFileContent(), PictureImportDTO[].class);

        return Arrays.stream(pictureImportDTOS)
                .map(this::importPicture)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPicture(PictureImportDTO pictureImportDTO) {
        if (!validationUtil.isValid(pictureImportDTO)) {
            return "Invalid Picture";
        }

        Optional<Picture> existingPicture = pictureRepository.findByPath(pictureImportDTO.getPath());
        if (existingPicture.isPresent()) {
            return "Invalid Picture";
        }

        Picture picture = modelMapper.map(pictureImportDTO, Picture.class);
        pictureRepository.save(picture);

        return String.format("Successfully imported Picture, with size %.2f", picture.getSize());
    }

    @Override
    public String exportPictures() {
        return pictureRepository.findAllBySizeGreaterThanOrderBySize(30000.0)
                .stream().map(Picture::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
