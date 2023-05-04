package example.services;

import example.entities.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> selectIngredientsByName(String name);

    List<Ingredient> selectIngredientsByNames(List<String> names);

    int deleteIngredientsByName(String ingredientName);

    int increaseAllIngredientsPrice(int percentage);

    int increaseAllIngredientsPriceNamedQuery();

    int increaseAllIngredientsInListPrice(int percentage, List<String> ingredientNames);

    int increaseAllIngredientsInListPriceNamedQuery(List<String> ingredientNames);
}