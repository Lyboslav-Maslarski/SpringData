package CarDealer.services;

import CarDealer.dtos.SupplierNoImporterDTO;
import CarDealer.dtos.SuppliersNoImporterDTO;
import CarDealer.entities.Supplier;
import CarDealer.repositories.SupplierRepository;
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
    public SuppliersNoImporterDTO getNoImporters() {
        List<Supplier> suppliers = supplierRepository.findAllByIsImporter(false);
        List<SupplierNoImporterDTO> list = suppliers.stream()
                .map(supplier -> {
                    SupplierNoImporterDTO dto = modelMapper.map(supplier, SupplierNoImporterDTO.class);
                    dto.setPartsCount(supplier.getParts().size());
                    return dto;
                })
                .collect(Collectors.toList());
        return new SuppliersNoImporterDTO(list);
    }
}
