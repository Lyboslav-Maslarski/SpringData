package example.services;

import example.entities.Ingredient;
import example.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> selectIngredientsByName(String name) {
        return this.ingredientRepository.findByNameStartingWith(name);
    }

    @Override
    public List<Ingredient> selectIngredientsByNames(List<String> names) {
        return this.ingredientRepository.findByNameInOrderByPriceAsc(names);
    }

    @Override
    public int deleteIngredientsByName(String ingredientName) {
        return this.ingredientRepository.deleteByName(ingredientName);
    }

    @Override
    public int increaseAllIngredientsPrice(int percentage) {
        BigDecimal updateMultiplier = new BigDecimal(1 + percentage / 100.0);
        return this.ingredientRepository.increaseAllIngredientsPrice(updateMultiplier);
    }

    @Override
    public int increaseAllIngredientsPriceNamedQuery() {
        return this.ingredientRepository.increaseAllIngredientsPriceBy20PercentsNamedQuery();
    }

    @Override
    public int increaseAllIngredientsInListPrice(int percentage, List<String> ingredientNames) {
        BigDecimal updateMultiplier = new BigDecimal(1 + percentage / 100.0);
        return this.ingredientRepository.increaseAllIngredientsPriceFromList(updateMultiplier, ingredientNames);
    }

    @Override
    public int increaseAllIngredientsInListPriceNamedQuery(List<String> ingredientNames) {
        return this.ingredientRepository.increaseIngredientsPriceBy20PercentsFromListNamedQuery(ingredientNames);
    }
}
