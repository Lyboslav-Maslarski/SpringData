package car_dealer.services;

import car_dealer.dtos.export_dtos.SaleDTO;

import java.util.List;

public interface SaleService {
    List<SaleDTO> getAllSales();
}
