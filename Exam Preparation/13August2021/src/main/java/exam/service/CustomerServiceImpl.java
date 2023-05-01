package exam.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.model.dtos.CustomerImportDTO;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.interfaces.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    public static final String CUSTOMERS_JSON = "src/main/resources/files/json/customers.json";
    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readAllLines(Path.of(CUSTOMERS_JSON))
                .stream().collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importCustomers() throws IOException {
        FileReader fileReader = new FileReader(CUSTOMERS_JSON);
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy")
                .create();
        CustomerImportDTO[] customerImportDTOS = gson.fromJson(fileReader, CustomerImportDTO[].class);

        return Arrays.stream(customerImportDTOS)
                .map(this::importCustomer)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importCustomer(CustomerImportDTO customerImportDTO) {
        if (!customerImportDTO.isValid()) {
            return "Invalid Customer";
        }
        if (customerRepository.findByEmail(customerImportDTO.getEmail()).isPresent()) {
            return "Invalid Customer";
        }

        Town town = townRepository.findByName(customerImportDTO.getTown().getName()).get();
        Customer customer = modelMapper.map(customerImportDTO, Customer.class);
        customer.setTown(town);

        customerRepository.save(customer);

        return String.format("Successfully imported Customer %s %s - %s",
                customer.getFirstName(),customer.getLastName(),customer.getEmail());
    }
}
