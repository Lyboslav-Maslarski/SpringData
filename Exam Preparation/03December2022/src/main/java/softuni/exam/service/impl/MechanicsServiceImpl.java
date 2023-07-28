package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicImportDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MechanicsServiceImpl implements MechanicsService {
    public static String MECHANICS_FILE_PATH = "src/main/resources/files/json/mechanics.json";
    private final MechanicsRepository mechanicsRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public MechanicsServiceImpl(MechanicsRepository mechanicsRepository, Gson gson,
                                ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.mechanicsRepository = mechanicsRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return mechanicsRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANICS_FILE_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        MechanicImportDTO[] mechanicImportDTOS = gson.fromJson(readMechanicsFromFile(), MechanicImportDTO[].class);

        return Arrays.stream(mechanicImportDTOS)
                .map(this::importMechanic)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importMechanic(MechanicImportDTO mechanicImportDTO) {
        if (!validationUtil.isValid(mechanicImportDTO)) {
            return "Invalid mechanic";
        }

        Optional<Mechanic> existingMechanic1 = mechanicsRepository.findByEmail(mechanicImportDTO.getEmail());
        if (existingMechanic1.isPresent()) {
            return "Invalid mechanic";
        }

        Optional<Mechanic> existingMechanic2 = mechanicsRepository.findByFirstName(mechanicImportDTO.getFirstName());
        if (existingMechanic2.isPresent()) {
            return "Invalid mechanic";
        }

        Mechanic mechanic = modelMapper.map(mechanicImportDTO, Mechanic.class);
        mechanicsRepository.save(mechanic);

        return String.format("Successfully imported mechanic %s %s", mechanic.getFirstName(), mechanic.getLastName());
    }
}
