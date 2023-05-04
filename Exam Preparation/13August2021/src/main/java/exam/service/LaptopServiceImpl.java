package exam.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.model.dtos.CustomerImportDTO;
import exam.model.dtos.LaptopImportDTO;
import exam.model.entities.Customer;
import exam.model.entities.Laptop;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.interfaces.LaptopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {
    public static final String LAPTOPS_JSON = "src/main/resources/files/json/laptops.json";
    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository, ModelMapper modelMapper) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readAllLines(Path.of(LAPTOPS_JSON))
                .stream().collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importLaptops() throws IOException {
        FileReader fileReader = new FileReader(LAPTOPS_JSON);
        Gson gson = new GsonBuilder().create();
        LaptopImportDTO[] laptopImportDTOS = gson.fromJson(fileReader, LaptopImportDTO[].class);

        return Arrays.stream(laptopImportDTOS)
                .map(this::importLaptop)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importLaptop(LaptopImportDTO laptopImportDTO) {
        if (!laptopImportDTO.isValid()) {
            return "Invalid Laptop";
        }
        if (laptopRepository.findByMacAddress(laptopImportDTO.getMacAddress()).isPresent()) {
            return "Invalid Laptop";
        }

        Shop shop = shopRepository.findByName(laptopImportDTO.getShop().getName()).get();
        Laptop laptop = modelMapper.map(laptopImportDTO, Laptop.class);
        laptop.setShop(shop);

        laptopRepository.save(laptop);

        return String.format("Successfully imported Laptop %s - %.2f - %d - %d",
                laptop.getMacAddress(), laptop.getCpuSpeed(), laptop.getRam(), laptop.getStorage());
    }

    @Override
    public String exportBestLaptops() {
        return this.laptopRepository
                .findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc()
                .stream()
                .map(Laptop::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
