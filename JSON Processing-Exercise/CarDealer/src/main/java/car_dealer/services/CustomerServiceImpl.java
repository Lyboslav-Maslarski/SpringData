package car_dealer.services;

import car_dealer.dtos.export_dtos.CustomerExportDTO;
import car_dealer.dtos.export_dtos.CustomerWithSalesDTO;
import car_dealer.entities.Part;
import car_dealer.repositories.CustomerRepository;
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
    public List<CustomerExportDTO> getAllOrderByBirthDate() {
        return customerRepository.findAllByOrderByBirthDateAscIsYoungDriverDesc().stream()
                .map(c -> modelMapper.map(c, CustomerExportDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CustomerWithSalesDTO> getAllWithSales() {
        return customerRepository.findAll().stream()
                .map(customer -> {
                    CustomerWithSalesDTO customerWithSalesDTO = new CustomerWithSalesDTO();
                    customerWithSalesDTO.setFullName(customer.getName());
                    customerWithSalesDTO.setBoughtCars(customer.getSales().size());
                    BigDecimal spentMoney = customer.getSales().stream()
                            .map(sale -> sale.getCar().getParts().stream()
                                    .map(Part::getPrice)
                                    .reduce(BigDecimal.ZERO,BigDecimal::add))
                            .reduce(BigDecimal.ZERO,BigDecimal::add);
                    customerWithSalesDTO.setSpendMoney(spentMoney);
                    return customerWithSalesDTO;
                }).sorted(Comparator.comparing(CustomerWithSalesDTO::getSpendMoney).reversed()
                        .thenComparing(CustomerWithSalesDTO::getBoughtCars))
                .collect(Collectors.toList());
    }
}
