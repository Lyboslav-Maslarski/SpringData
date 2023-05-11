package car_dealer.services;

import car_dealer.dtos.import_dtos.CarDTO;
import car_dealer.dtos.import_dtos.CustomerDTO;
import car_dealer.dtos.import_dtos.PartDTO;
import car_dealer.dtos.import_dtos.SupplierDTO;
import car_dealer.entities.*;
import car_dealer.repositories.*;
import car_dealer.repositories.entities.*;
import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {
    private static final String SUPPLIERS_JSON_PATH = "src/main/resources/suppliers.json";
    private static final String PARTS_JSON_PATH = "src/main/resources/parts.json";
    private static final String CARS_JSON_PATH = "src/main/resources/cars.json";
    private static final String CUSTOMERS_JSON_PATH = "src/main/resources/customers.json";
    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Random random;

    @Autowired
    public SeedServiceImpl(SupplierRepository supplierRepository, PartRepository partRepository,
                           CarRepository carRepository, CustomerRepository customerRepository,
                           SaleRepository saleRepository) {
        this.supplierRepository = supplierRepository;
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;

        JsonDeserializer<LocalDateTime> toLocalDate =
                (json, t, c) -> LocalDateTime.parse(json.getAsString());
        this.random = new Random();
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class,toLocalDate)
                .create();
    }

    @Override
    public void seedSuppliers() throws FileNotFoundException {
        FileReader fileReader = new FileReader(SUPPLIERS_JSON_PATH);
        SupplierDTO[] supplierDTOS = gson.fromJson(fileReader, SupplierDTO[].class);

        List<Supplier> collect = Arrays.stream(supplierDTOS)
                .map(s -> modelMapper.map(s, Supplier.class))
                .collect(Collectors.toList());

        supplierRepository.saveAll(collect);
    }

    @Override
    public void seedParts() throws FileNotFoundException {
        FileReader fileReader = new FileReader(PARTS_JSON_PATH);
        PartDTO[] partDTOS = gson.fromJson(fileReader, PartDTO[].class);


        List<Part> collect = Arrays.stream(partDTOS)
                .map(p -> modelMapper.map(p, Part.class))
                .map(this::setRandomSeller)
                .collect(Collectors.toList());

        partRepository.saveAll(collect);
    }

    private Part setRandomSeller(Part part) {
        List<Supplier> suppliers = supplierRepository.findAll();
        part.setSupplier(suppliers.get(random.nextInt(suppliers.size())));
        return part;
    }

    @Override
    public void seedCars() throws FileNotFoundException {
        FileReader fileReader = new FileReader(CARS_JSON_PATH);
        CarDTO[] carDTOS = gson.fromJson(fileReader, CarDTO[].class);

        List<Car> collect = Arrays.stream(carDTOS)
                .map(c -> modelMapper.map(c, Car.class))
                .map(this::setRandomParts)
                .collect(Collectors.toList());

        carRepository.saveAll(collect);
    }

    private Car setRandomParts(Car car) {
        List<Part> parts = partRepository.findAll();
        List<Part> toAdd = new ArrayList<>();
        for (int i = 0; i < random.nextInt(3, 6); i++) {
            toAdd.add(parts.get(random.nextInt(parts.size())));
        }
        car.setParts(toAdd);
        return car;
    }

    @Override
    public void seedCustomers() throws FileNotFoundException {
        FileReader fileReader = new FileReader(CUSTOMERS_JSON_PATH);
        CustomerDTO[] customerDTOS = gson.fromJson(fileReader, CustomerDTO[].class);

        List<Customer> collect = Arrays.stream(customerDTOS)
                .map(c -> modelMapper.map(c, Customer.class))
                .collect(Collectors.toList());

        customerRepository.saveAll(collect);
    }

    @Override
    public void seedSales() {
        List<Car> cars = carRepository.findAll();
        List<Customer> customers = customerRepository.findAll();
        double[] discounts = {0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5};

        for (Car car : cars) {
            Sale sale = new Sale();
            sale.setCustomer(customers.get(random.nextInt(customers.size())));
            sale.setCar(car);
            sale.setDiscount(discounts[random.nextInt(discounts.length)]);
            saleRepository.save(sale);
        }
    }
}
