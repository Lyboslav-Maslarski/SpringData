package example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import example.dtos.CategoryStats;
import example.dtos.ProductWithoutBuyerDTO;
import example.dtos.UserWithSoldProductsDTO;
import example.dtos.UsersWithSoldProductsDTO;
import example.services.ProductService;
import example.services.SeedService;
import example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductShopRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final Gson gson;

    @Autowired
    public ProductShopRunner(SeedService seedService, ProductService productService, UserService userService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    }

    @Override
    public void run(String... args) throws Exception {
//        seedService.seedAll();
//        queryOne();
//        queryTwo();
        queryThree();
//        queryFour();
    }

    private void queryFour() {
        UsersWithSoldProductsDTO allWithSoldProductsOrderByCount = userService.getAllWithSoldProductsOrderByCount();
        String json = gson.toJson(allWithSoldProductsOrderByCount);
        System.out.println(json);
    }

    private void queryThree() {
        List<CategoryStats> categoryStatistics = productService.getCategoryStatistics();
        String json = gson.toJson(categoryStatistics);
        System.out.println(json);
    }

    private void queryTwo() {
        List<UserWithSoldProductsDTO> allWithSoldProducts = userService.getAllWithSoldProducts();
        String json = gson.toJson(allWithSoldProducts);
        System.out.println(json);
    }

    private void queryOne() {
        List<ProductWithoutBuyerDTO> productsWithoutSeller = productService.getProductsInPriceRangeForSell(500, 1000);
        String json = gson.toJson(productsWithoutSeller);
        System.out.println(json);
    }
}
