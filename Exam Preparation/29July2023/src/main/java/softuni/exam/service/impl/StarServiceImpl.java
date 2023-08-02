package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.StarImportDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StarServiceImpl implements StarService {
    private final StarRepository starRepository;
    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public StarServiceImpl(StarRepository starRepository, ConstellationRepository constellationRepository,
                           Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/stars.json"));
    }

    @Override
    public String importStars() throws IOException {
        StarImportDTO[] starImportDTOS =
                gson.fromJson(readStarsFileContent(), StarImportDTO[].class);

        return Arrays.stream(starImportDTOS)
                .map(this::importStar)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importStar(StarImportDTO starImportDTO) {
        if (!validationUtil.isValid(starImportDTO) ||
                starRepository.findByName(starImportDTO.getName()).isPresent()) {
            return "Invalid star";
        }

        Optional<Constellation> constellation = constellationRepository.findById(starImportDTO.getConstellation());
        if (constellation.isEmpty()) {
            return "Invalid star";
        }

        Star star = modelMapper.map(starImportDTO, Star.class);
        star.setConstellation(constellation.get());
        starRepository.save(star);

        return String.format("Successfully imported star %s - %.2f light years",
                star.getName(), star.getLightYears());
    }

    @Override
    @Transactional
    public String exportStars() {
        return starRepository.findAllByStarTypeAndObserversIsEmptyOrderByLightYears(StarType.RED_GIANT)
                .stream()
                .map(Star::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
