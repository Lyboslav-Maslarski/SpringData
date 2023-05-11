package CarDealer.services;

import CarDealer.dtos.import_dtos.CarsDTO;
import CarDealer.dtos.import_dtos.CustomersDTO;
import CarDealer.dtos.import_dtos.PartsDTO;
import CarDealer.dtos.import_dtos.SuppliersDTO;
import CarDealer.entities.*;
import CarDealer.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {
    private static final String SUPPLIERS_XML_PATH = "src/main/resources/import/CarDealer/suppliers.xml";
    private static final String PARTS_XML_PATH = "src/main/resources/import/CarDealer/parts.xml";
    private static final String CARS_XML_PATH = "src/main/resources/import/CarDealer/cars.xml";
    private static final String CUSTOMERS_XML_PATH = "src/main/resources/import/CarDealer/customers.xml";
    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
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

        this.random = new Random();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void seedSuppliers() throws FileNotFoundException, JAXBException {
        FileReader fileReader = new FileReader(SUPPLIERS_XML_PATH);

        JAXBContext context = JAXBContext.newInstance(SuppliersDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        SuppliersDTO suppliersDTO = (SuppliersDTO) unmarshaller.unmarshal(fileReader);

        List<Supplier> collect = suppliersDTO.getSuppliers().stream()
                .map(s -> modelMapper.map(s, Supplier.class))
                .collect(Collectors.toList());

        supplierRepository.saveAll(collect);
    }

    @Override
    public void seedParts() throws FileNotFoundException, JAXBException {
        FileReader fileReader = new FileReader(PARTS_XML_PATH);

        JAXBContext context = JAXBContext.newInstance(PartsDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PartsDTO partsDTO = (PartsDTO) unmarshaller.unmarshal(fileReader);

        List<Part> collect = partsDTO.getParts().stream()
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
    public void seedCars() throws FileNotFoundException, JAXBException {
        FileReader fileReader = new FileReader(CARS_XML_PATH);

        JAXBContext context = JAXBContext.newInstance(CarsDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CarsDTO carsDTO = (CarsDTO) unmarshaller.unmarshal(fileReader);

        List<Car> collect = carsDTO.getCars().stream()
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
    public void seedCustomers() throws FileNotFoundException, JAXBException {
        FileReader fileReader = new FileReader(CUSTOMERS_XML_PATH);

        JAXBContext context = JAXBContext.newInstance(CustomersDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CustomersDTO customersDTO = (CustomersDTO) unmarshaller.unmarshal(fileReader);

        List<Customer> collect = customersDTO.getCustomers().stream()
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
