package CarDealer.services;

import CarDealer.dtos.CustomersExportDTO;
import CarDealer.dtos.CustomersWithSalesDTO;

public interface CustomerService {
    CustomersExportDTO getAllOrderByBirthDate();

    CustomersWithSalesDTO getAllWithSales();
}
