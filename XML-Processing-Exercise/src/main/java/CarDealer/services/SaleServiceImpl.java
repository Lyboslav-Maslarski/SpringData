package CarDealer.services;

import CarDealer.dtos.export_dtos.SaleDTO;
import CarDealer.dtos.export_dtos.SalesDTO;
import CarDealer.entities.Part;
import CarDealer.repositories.SaleRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    @Transactional
    public SalesDTO getAllSales() {
        List<SaleDTO> dtos = saleRepository.findAll().stream()
                .map(sale -> {
                    SaleDTO saleDTO = modelMapper.map(sale, SaleDTO.class);
                    BigDecimal price = sale.getCar().getParts().stream()
                            .map(Part::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    saleDTO.setPrice(price);
                    saleDTO.setPriceWithDiscount(price.subtract(price.multiply(BigDecimal.valueOf(sale.getDiscount()))));
                    return saleDTO;
                })
                .collect(Collectors.toList());
        return new SalesDTO(dtos);
    }
}
