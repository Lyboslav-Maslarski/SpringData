package ProductShop.services;

import ProductShop.entities.Product;
import ProductShop.exportDTOS.CategoriesExportDTO;
import ProductShop.exportDTOS.CategoryExportDTO;
import ProductShop.exportDTOS.ProductInRangeDTO;
import ProductShop.exportDTOS.ProductsInRangeDTO;
import ProductShop.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

        this.mapper = new ModelMapper();
    }

    @Override
    public ProductsInRangeDTO getAllInRange(float from, float to) {
        BigDecimal rangeFrom = BigDecimal.valueOf(from);
        BigDecimal rangeTo = BigDecimal.valueOf(to);

        List<Product> products = this.productRepository.
                findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(rangeFrom, rangeTo);

        List<ProductInRangeDTO> dtos = products.stream()
                .map(p -> {
                    ProductInRangeDTO dto = mapper.map(p, ProductInRangeDTO.class);
                    dto.setSeller(p.getSeller().getFullName());
                    return dto;
                })
                .collect(Collectors.toList());

        return new ProductsInRangeDTO(dtos);
    }

    @Override
    public CategoriesExportDTO getCategoriesStats() {
        List<CategoryExportDTO> categoryStats = productRepository.getCategoryStats();
        return new CategoriesExportDTO(categoryStats);
    }
}
