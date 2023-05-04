package example.repositories;

import example.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByNameStartingWith(String name);

    List<Ingredient> findByNameInOrderByPriceAsc(List<String> names);

    @Transactional
    int deleteByName(String ingredientName);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient AS i SET i.price = i.price * :multiplier")
    int increaseAllIngredientsPrice(@Param("multiplier") BigDecimal updateMultiplier);

    @Modifying
    @Transactional
    int increaseAllIngredientsPriceBy20PercentsNamedQuery();

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient AS i SET i.price = i.price * :multiplier WHERE i.name IN :names")
    int increaseAllIngredientsPriceFromList(@Param("multiplier") BigDecimal updateMultiplier, @Param("names") List<String> ingredientNames);

    @Modifying
    @Transactional
    int increaseIngredientsPriceBy20PercentsFromListNamedQuery(@Param("names") List<String> ingredientNames);
}
