package ProductShop.repositories;

import ProductShop.entities.Product;
import ProductShop.exportDTOS.CategoryExportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal rangeFrom, BigDecimal rangeTo);

    @Query("SELECT new ProductShop.exportDTOS.CategoryExportDTO(c.name AS name, COUNT(p) AS productsCount, " +
           "AVG(p.price) AS averagePrice, SUM(p.price) AS totalRevenue) " +
           "FROM Product p " +
           "JOIN p.categories c " +
           "GROUP BY c " +
           "ORDER BY productsCount")
    List<CategoryExportDTO> getCategoryStats();
}
