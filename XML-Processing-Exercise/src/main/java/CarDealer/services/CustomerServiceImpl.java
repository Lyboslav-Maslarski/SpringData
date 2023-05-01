package CarDealer.services;

import CarDealer.dtos.CustomerExportDTO;
import CarDealer.dtos.CustomerWithSalesDTO;
import CarDealer.dtos.CustomersExportDTO;
import CarDealer.dtos.CustomersWithSalesDTO;
import CarDealer.entities.Part;
import CarDealer.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    @Transactional
    public CustomersExportDTO getAllOrderByBirthDate() {
        List<CustomerExportDTO> list = customerRepository.findAllByOrderByBirthDateAscIsYoungDriverAsc().stream()
                .map(c -> modelMapper.map(c, CustomerExportDTO.class))
                .collect(Collectors.toList());
        return new CustomersExportDTO(list);
    }

    @Override
    @Transactional
    public CustomersWithSalesDTO getAllWithSales() {
        List<CustomerWithSalesDTO> dtos = customerRepository.findAll().stream()
                .map(customer -> {
                    CustomerWithSalesDTO customerWithSalesDTO = new CustomerWithSalesDTO();
                    customerWithSalesDTO.setFullName(customer.getName());
                    customerWithSalesDTO.setBoughtCars(customer.getSales().size());
                    BigDecimal spentMoney = customer.getSales().stream()
                            .map(sale -> sale.getCar().getParts().stream()
                                    .map(Part::getPrice)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    customerWithSalesDTO.setSpentMoney(spentMoney);
                    return customerWithSalesDTO;
                }).sorted(Comparator.comparing(CustomerWithSalesDTO::getSpentMoney).reversed()
                        .thenComparing(CustomerWithSalesDTO::getBoughtCars))
                .collect(Collectors.toList());
        return new CustomersWithSalesDTO(dtos);
    }
}
