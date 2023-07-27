package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.SellerImportDTO;
import softuni.exam.models.dtos.SellersWrapperDTO;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Rating;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/sellers.xml"));
    }

    @Override
    public String importSellers() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(SellersWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        SellersWrapperDTO sellersWrapperDTO =
                (SellersWrapperDTO) unmarshaller.unmarshal(new File("src/main/resources/files/xml/sellers.xml"));

        return sellersWrapperDTO.getSellers()
                .stream()
                .map(this::importSeller)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importSeller(SellerImportDTO sellerImportDTO) {
        if (!validationUtil.isValid(sellerImportDTO)) {
            return "Invalid seller";
        }

        Optional<Seller> existingSeller = sellerRepository
                .findByEmail(sellerImportDTO.getEmail());
        if (existingSeller.isPresent()){
            return "Invalid seller";
        }

        boolean existingRating = Arrays.stream(Rating.values()).anyMatch(r -> r.name().equals(sellerImportDTO.getRating()));
        if (!existingRating){
            return "Invalid seller";
        }

        Seller seller = modelMapper.map(sellerImportDTO, Seller.class);
        seller.setRating(Rating.valueOf(sellerImportDTO.getRating()));
        sellerRepository.save(seller);

        return String.format("Successfully imported seller %s - %s", seller.getLastName(), seller.getEmail());
    }
}
