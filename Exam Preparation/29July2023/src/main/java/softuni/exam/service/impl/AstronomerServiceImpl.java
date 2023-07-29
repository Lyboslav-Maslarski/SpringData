package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerImportDTO;
import softuni.exam.models.dto.AstronomerWrapperDTO;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    private final AstronomerRepository astronomerRepository;
    private final StarRepository starRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, StarRepository starRepository,
                                 XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/astronomers.xml"));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        AstronomerWrapperDTO astronomerWrapperDTO = xmlParser.
                parseXml(AstronomerWrapperDTO.class, "src/main/resources/files/xml/astronomers.xml");

        return astronomerWrapperDTO.getAstronomers().stream()
                .map(this::importAstronomer)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importAstronomer(AstronomerImportDTO astronomerImportDTO) {
        if (!validationUtil.isValid(astronomerImportDTO)) {
            return "Invalid astronomer";
        }

        Optional<Astronomer> existingAstronomer = astronomerRepository.
                findByFirstNameAndLastName(astronomerImportDTO.getFirstName(), astronomerImportDTO.getLastName());
        if (existingAstronomer.isPresent()) {
            return "Invalid astronomer";
        }


        Optional<Star> star = starRepository.findById(astronomerImportDTO.getObservingStarId());
        if (star.isEmpty()) {
            return "Invalid astronomer";
        }

        Astronomer astronomer = modelMapper.map(astronomerImportDTO, Astronomer.class);
        astronomer.setObservingStar(star.get());
        astronomer.setBirthday(LocalDate.parse(astronomerImportDTO.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        astronomerRepository.save(astronomer);

        return String.format("Successfully imported astronomer %s - %.2f",
                astronomer.getFullName(), astronomer.getAverageObservationHours());
    }
}
