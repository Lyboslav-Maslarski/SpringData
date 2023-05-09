package example.services;

import example.dtos.CategoryStats;
import example.dtos.ProductWithoutBuyerDTO;
import example.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductWithoutBuyerDTO> getProductsInPriceRangeForSell(float from, float to) {
        BigDecimal rangeStart = new BigDecimal(from);
        BigDecimal rangeEnd = new BigDecimal(to);
        return productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(rangeStart,rangeEnd);
    }

    @Override
    public List<CategoryStats> getCategoryStatistics() {
       return productRepository.getCategoryStats();
    }
}
