package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.OfferImportDTO;
import softuni.exam.models.dtos.OffersWrapperDTO;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, CarRepository carRepository,
                            SellerRepository sellerRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
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
    public String importOffers() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(OffersWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        OffersWrapperDTO offersWrapperDTO =
                (OffersWrapperDTO) unmarshaller.unmarshal(new File("src/main/resources/files/xml/offers.xml"));

        return offersWrapperDTO.getOffers()
                .stream()
                .map(this::importOffer)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importOffer(OfferImportDTO offerImportDTO) {
        if (!validationUtil.isValid(offerImportDTO)) {
            return "Invalid offer";
        }

        LocalDateTime addedOn = LocalDateTime.parse(offerImportDTO.getAddedOn(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Optional<Offer> existingOffer = offerRepository
                .findByDescriptionAndAddedOn(offerImportDTO.getDescription(), addedOn);
        if (existingOffer.isPresent()) {
            return "Invalid offer";
        }

        Optional<Car> car = carRepository
                .findById(offerImportDTO.getCar().getId());
        if (car.isEmpty()) {
            return "Invalid offer";
        }

        Optional<Seller> seller = sellerRepository
                .findById(offerImportDTO.getSeller().getId());
        if (seller.isEmpty()) {
            return "Invalid offer";
        }

        Offer offer = modelMapper.map(offerImportDTO, Offer.class);
        offer.setCar(car.get());
        offer.setSeller(seller.get());
        offer.setAddedOn(addedOn);
        offerRepository.save(offer);

        return String.format("Successfully imported offer %s - %s", offer.getAddedOn(), offer.getHasGoldStatus());
    }
}
