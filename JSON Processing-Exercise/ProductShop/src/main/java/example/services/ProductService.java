package example.services;

import example.dtos.CategoryStats;
import example.dtos.ProductWithoutBuyerDTO;

import java.util.List;

public interface ProductService {
    List<ProductWithoutBuyerDTO> getProductsInPriceRangeForSell(float from, float to);

    List<CategoryStats> getCategoryStatistics();
}
