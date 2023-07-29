package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationImportDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository, Gson gson,
                                    ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/constellations.json"));
    }

    @Override
    public String importConstellations() throws IOException {
        ConstellationImportDTO[] constellationImportDTOS =
                gson.fromJson(readConstellationsFromFile(), ConstellationImportDTO[].class);

        return Arrays.stream(constellationImportDTOS)
                .map(this::importConstellation)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importConstellation(ConstellationImportDTO constellationImportDTO) {
        if (!validationUtil.isValid(constellationImportDTO) ||
                constellationRepository.findByName(constellationImportDTO.getName()).isPresent()) {
            return "Invalid constellation";
        }

        Constellation constellation = modelMapper.map(constellationImportDTO, Constellation.class);
        constellationRepository.save(constellation);

        return String.format("Successfully imported constellation %s - %s",
                constellation.getName(), constellation.getDescription());
    }
}
