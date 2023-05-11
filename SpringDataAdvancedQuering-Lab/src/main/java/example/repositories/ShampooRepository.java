package example.repositories;

import example.entities.Shampoo;
import example.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findBySizeOrderByIdAsc(Size size);

    List<Shampoo> findBySizeOrLabelIdOrderByPrice(Size size, long labelId);

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo s" +
           " JOIN s.ingredients AS i" +
           " WHERE i.name IN :names")
    List<Shampoo> findByIngredientsIn(@Param("names") Set<String> ingredientsNames);

    @Query("SELECT s FROM Shampoo s" +
           " WHERE s.ingredients.size < :count")
    List<Shampoo> findByIngredientsCount(@Param("count") int ingredientCount);
}
