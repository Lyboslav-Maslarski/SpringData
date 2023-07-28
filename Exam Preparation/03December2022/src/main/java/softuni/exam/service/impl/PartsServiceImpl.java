package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartImportDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartsServiceImpl implements PartsService {
    public static String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";
    private final PartsRepository partsRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public PartsServiceImpl(PartsRepository partsRepository, Gson gson,
                            ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.partsRepository = partsRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return partsRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_FILE_PATH));
    }

    @Override
    public String importParts() throws IOException {
        PartImportDTO[] partImportDTOS = gson.fromJson(readPartsFileContent(), PartImportDTO[].class);

        return Arrays.stream(partImportDTOS)
                .map(this::importPart)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPart(PartImportDTO partImportDTO) {
        if (!validationUtil.isValid(partImportDTO)) {
            return "Invalid part";
        }

        Optional<Part> existingPart = partsRepository.findByPartName(partImportDTO.getPartName());
        if (existingPart.isPresent()) {
            return "Invalid part";
        }

        Part part = modelMapper.map(partImportDTO, Part.class);
        partsRepository.save(part);

        return String.format("Successfully imported part %s - %s", part.getPartName(), part.getPrice());
    }
}
