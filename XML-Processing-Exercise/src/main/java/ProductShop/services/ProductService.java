package ProductShop.services;


import ProductShop.exportDTOS.CategoriesExportDTO;
import ProductShop.exportDTOS.ProductsInRangeDTO;

public interface ProductService {

    ProductsInRangeDTO getAllInRange(float from, float to);

    CategoriesExportDTO getCategoriesStats();
}
