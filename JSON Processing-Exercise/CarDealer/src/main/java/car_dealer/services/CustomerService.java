package car_dealer.services;

import car_dealer.dtos.export_dtos.CustomerExportDTO;
import car_dealer.dtos.export_dtos.CustomerWithSalesDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerExportDTO> getAllOrderByBirthDate();

    List<CustomerWithSalesDTO> getAllWithSales();
}
