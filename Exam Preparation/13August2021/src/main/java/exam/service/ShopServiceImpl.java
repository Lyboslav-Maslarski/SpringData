package exam.service;

import exam.model.dtos.ShopImportDTO;
import exam.model.dtos.ShopWrapperDTO;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.interfaces.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {
    public static final String SHOPS_XML = "src/main/resources/files/xml/shops.xml";
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readAllLines(Path.of(SHOPS_XML))
                .stream().collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        FileReader fileReader = new FileReader(SHOPS_XML);

        JAXBContext context = JAXBContext.newInstance(ShopWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ShopWrapperDTO dto = (ShopWrapperDTO) unmarshaller.unmarshal(fileReader);

        return dto.getList().stream()
                .map(this::importShop)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importShop(ShopImportDTO shopImportDTO) {
        if (!shopImportDTO.isValid()) {
            return "Invalid shop";
        }
        if (shopRepository.findByName(shopImportDTO.getName()).isPresent()) {
            return "Invalid shop";
        }
        Town town = townRepository.findByName(shopImportDTO.getTown().getName()).get();
        Shop shop = modelMapper.map(shopImportDTO, Shop.class);
        shop.setTown(town);
        shopRepository.save(shop);
        return "Successfully imported Shop " + shop.getName() + " - " + shop.getIncome();
    }
}
