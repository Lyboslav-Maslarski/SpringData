package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferImportDTO;
import softuni.exam.models.dto.OffersWrapperDTO;
import softuni.exam.models.entity.*;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
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
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ApartmentRepository apartmentRepository;
    private final AgentRepository agentRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public OfferServiceImpl(OfferRepository offerRepository, ApartmentRepository apartmentRepository,
                            AgentRepository agentRepository, XmlParser xmlParser,
                            ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.offerRepository = offerRepository;
        this.apartmentRepository = apartmentRepository;
        this.agentRepository = agentRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/offers.xml"));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        OffersWrapperDTO offersWrapperDTO =
                xmlParser.parseXml(OffersWrapperDTO.class, "src/main/resources/files/xml/offers.xml");

        return offersWrapperDTO.getOffers()
                .stream()
                .map(this::importOffer)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importOffer(OfferImportDTO offerImportDTO) {
        if (!validationUtil.isValid(offerImportDTO)) {
            return "Invalid offer";
        }

        Optional<Agent> agent = agentRepository.findByFirstName(offerImportDTO.getAgent().getName());
        if (agent.isEmpty()) {
            return "Invalid offer";
        }

        Optional<Apartment> apartment = apartmentRepository.findById(offerImportDTO.getApartment().getId());
        if (apartment.isEmpty()) {
            return "Invalid offer";
        }

        Offer offer = modelMapper.map(offerImportDTO, Offer.class);
        offer.setAgent(agent.get());
        offer.setApartment(apartment.get());
        offer.setPublishedOn(LocalDate.parse(offerImportDTO.getPublishedOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        offerRepository.save(offer);

        return String.format("Successfully imported offer %.2f", offer.getPrice());
    }

    @Override
    public String exportOffers() {
        return offerRepository.findAllByApartmentApartmentTypeOrderByApartmentAreaDescPriceAsc(ApartmentType.three_rooms)
                .stream()
                .map(Offer::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
