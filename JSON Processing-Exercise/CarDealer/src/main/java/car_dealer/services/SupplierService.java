package car_dealer.services;

import car_dealer.dtos.export_dtos.SupplierNoImporterDTO;

import java.util.List;

public interface SupplierService {
    List<SupplierNoImporterDTO> getNoImporters();
}
