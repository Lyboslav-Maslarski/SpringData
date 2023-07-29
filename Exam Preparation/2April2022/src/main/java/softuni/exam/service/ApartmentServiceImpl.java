package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDTO;
import softuni.exam.models.dto.ApartmentsWrapperDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository,
                                XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/apartments.xml"));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        ApartmentsWrapperDTO apartmentsWrapperDTO =
                xmlParser.parseXml(ApartmentsWrapperDTO.class, "src/main/resources/files/xml/apartments.xml");

        return apartmentsWrapperDTO.getApartments()
                .stream()
                .map(this::importApartment)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importApartment(ApartmentImportDTO apartmentImportDTO) {
        if (!validationUtil.isValid(apartmentImportDTO) ||
                apartmentRepository.findByAreaAndTownTownName(apartmentImportDTO.getArea(),apartmentImportDTO.getTown()).isPresent()) {
            return "Invalid apartment";
        }

        Optional<Town> town = townRepository.findByTownName(apartmentImportDTO.getTown());
        if (town.isEmpty()) {
            return "Invalid apartment";
        }

        Apartment apartment = modelMapper.map(apartmentImportDTO, Apartment.class);
        apartment.setTown(town.get());
        apartmentRepository.save(apartment);

        return String.format("Successfully imported apartment %s %.2f", apartment.getApartmentType(), apartment.getArea());
    }
}
