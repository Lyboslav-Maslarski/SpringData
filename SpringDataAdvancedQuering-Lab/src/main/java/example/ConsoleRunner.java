package example;

import example.entities.Shampoo;
import example.entities.Size;
import example.services.IngredientService;
import example.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public ConsoleRunner(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("============= 1. selectShampoosBySize(Size.MEDIUM) =============");
        this.shampooService.selectShampoosBySize(Size.MEDIUM).forEach(System.out::println);

        System.out.println("============= 2. selectShampoosBySizeOrLabel(Size.MEDIUM, Label ID 10) =============");
        this.shampooService.selectShampoosBySizeOrLabel(Size.MEDIUM,10).forEach(System.out::println);

        System.out.println("============= 3. selectShampoosByPrice(BigDecimal.valueOf(5)) =============");
        this.shampooService.selectShampoosByPrice(BigDecimal.valueOf(5)).forEach(System.out::println);

        System.out.println("============= 4. selectIngredientsByName(\"M\") =============");
        this.ingredientService.selectIngredientsByName("M").forEach(System.out::println);

        System.out.println("============= 5. selectIngredientsByNames(\"Lavender\", \"Herbs\", \"Apple\") =============");
        this.ingredientService.selectIngredientsByNames(List.of("Lavender","Herbs","Apple")).forEach(System.out::println);

        System.out.println("============= 6. countAllByPriceIsLessThan(8.5) =============");
        System.out.println(this.shampooService.countShampoosByPrice(BigDecimal.valueOf(8.5)));

        System.out.println("============= 7. selectShampoosByIngredients(Berry, Mineral-Collagen) =============");
        this.shampooService.selectShampoosByIngredients(Set.of("Berry", "Mineral-Collagen")).stream().map(Shampoo::getBrand).distinct().forEach(System.out::println);

        System.out.println("============= 8. findAllByIngredientsCountLess(2) =============");
        this.shampooService.selectShampoosByIngredientsCount(2).stream().map(Shampoo::getBrand).forEach(System.out::println);

        System.out.println("============= 9. deleteIngredientsByName(\"Active-Caffeine\") =============");
        System.out.println(this.ingredientService.deleteIngredientsByName("Active-Caffeine"));

        System.out.println("============= 10. increaseAllIngredientsPrice() =============");
        System.out.println(this.ingredientService.increaseAllIngredientsPrice(20));

        System.out.println("============= 10. increaseAllIngredientsPriceNamedQuery() =============");
        System.out.println(this.ingredientService.increaseAllIngredientsPriceNamedQuery());

        System.out.println("============= 11. increaseAllIngredientsInListPrice(\"Lavender\", \"Herbs\", \"Apple\") =============");
        System.out.println(this.ingredientService.increaseAllIngredientsInListPrice(20, List.of("Lavender", "Herbs", "Apple")));

        System.out.println("============= 11. increaseAllIngredientsInListPriceNamedQuery(\"Lavender\", \"Herbs\", \"Apple\") =============");
        System.out.println(this.ingredientService.increaseAllIngredientsInListPriceNamedQuery(List.of("Lavender", "Herbs", "Apple")));
    }
}
