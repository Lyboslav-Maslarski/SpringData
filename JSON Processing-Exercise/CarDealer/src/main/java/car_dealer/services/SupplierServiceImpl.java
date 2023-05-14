package car_dealer.services;

import car_dealer.dtos.export_dtos.SupplierNoImporterDTO;
import car_dealer.entities.Supplier;
import car_dealer.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    @Transactional
    public List<SupplierNoImporterDTO> getNoImporters() {
        List<Supplier> suppliers = supplierRepository.findAllByIsImporter(false);
        return suppliers.stream()
                .map(supplier -> {
                    SupplierNoImporterDTO dto = modelMapper.map(supplier, SupplierNoImporterDTO.class);
                    dto.setPartsCount(supplier.getParts().size());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
