package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PlaneImportDTO;
import softuni.exam.models.dtos.PlanesWrapperDTO;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository, ModelMapper modelMapper,
                            XmlParser xmlParser, ValidationUtil validationUtil) {
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/planes.xml"));
    }

    @Override
    public String importPlanes() throws JAXBException {
        PlanesWrapperDTO planesWrapperDTO = xmlParser
                .parseXml(PlanesWrapperDTO.class, "src/main/resources/files/xml/planes.xml");

        return planesWrapperDTO.getPlanes().stream()
                .map(this::importPlane)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPlane(PlaneImportDTO planeImportDTO) {
        if (!validationUtil.isValid(planeImportDTO)) {
            return "Invalid Plane";
        }

        Optional<Plane> existingPlane = planeRepository.findByRegisterNumber(planeImportDTO.getRegisterNumber());
        if (existingPlane.isPresent()) {
            return "Invalid Plane";
        }

        Plane plane = modelMapper.map(planeImportDTO, Plane.class);
        planeRepository.save(plane);

        return String.format("Successfully imported Plane %s", plane.getRegisterNumber());
    }
}
