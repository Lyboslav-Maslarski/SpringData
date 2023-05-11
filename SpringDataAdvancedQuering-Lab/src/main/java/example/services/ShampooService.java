package example.services;

import example.entities.Shampoo;
import example.entities.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {
    List<Shampoo> selectShampoosBySize(Size size);

    List<Shampoo> selectShampoosBySizeOrLabel(Size size, int labelId);

    List<Shampoo> selectShampoosByPrice(BigDecimal price);

    int countShampoosByPrice(BigDecimal price);

    List<Shampoo> selectShampoosByIngredients(Set<String> ingredientsNames);

    List<Shampoo> selectShampoosByIngredientsCount(int ingredientCount);
}
