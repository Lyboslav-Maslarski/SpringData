package example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import example.dtos.CategoryImportDTO;
import example.dtos.ProductImportDTO;
import example.dtos.UserImportDTO;
import example.entities.Category;
import example.entities.Product;
import example.entities.User;
import example.repositories.CategoryRepository;
import example.repositories.ProductRepository;
import example.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private static final String USERS_JSON_PATH = "src/main/resources/users.json";
    private static final String CATEGORIES_JSON_PATH = "src/main/resources/categories.json";
    private static final String PRODUCTS_JSON_PATH = "src/main/resources/products.json";
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void seedUser() throws FileNotFoundException {
        FileReader fileReader = new FileReader(USERS_JSON_PATH);
        UserImportDTO[] userImportDTOS = this.gson.fromJson(fileReader, UserImportDTO[].class);

        List<User> users = Arrays.stream(userImportDTOS)
                .map(importDTO -> this.modelMapper.map(importDTO, User.class))
                .collect(Collectors.toList());

        userRepository.saveAll(users);
    }

    @Override
    public void seedCategories() throws FileNotFoundException {
        FileReader fileReader = new FileReader(CATEGORIES_JSON_PATH);
        CategoryImportDTO[] categoryImportDTOS = this.gson.fromJson(fileReader, CategoryImportDTO[].class);

        List<Category> categories = Arrays.stream(categoryImportDTOS)
                .map(importDTO -> this.modelMapper.map(importDTO, Category.class))
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);
    }

    @Override
    public void seedProducts() throws FileNotFoundException {
        FileReader fileReader = new FileReader(PRODUCTS_JSON_PATH);
        ProductImportDTO[] productImportDTOS = this.gson.fromJson(fileReader, ProductImportDTO[].class);

        List<Product> products = Arrays.stream(productImportDTOS)
                .map(importDTO -> this.modelMapper.map(importDTO, Product.class))
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .map(this::setRandomCategory)
                .collect(Collectors.toList());

        productRepository.saveAll(products);
    }

    private Product setRandomCategory(Product product) {
        long categoriesCount = categoryRepository.count();

        Random random = new Random();
        int count = random.nextInt((int) categoriesCount);

        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < count; i++) {
            int randomId = random.nextInt((int) categoriesCount) + 1;
            Optional<Category> category = categoryRepository.findById(randomId);
            categories.add(category.get());
        }

        product.setCategories(categories);
        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(944)) > 0) {
            return product;
        }
        Optional<User> buyer = getRandomUser();
        product.setBuyer(buyer.get());
        return product;
    }

    private Product setRandomSeller(Product product) {
        Optional<User> seller = getRandomUser();
        product.setSeller(seller.get());
        return product;
    }

    private Optional<User> getRandomUser() {
        long usersCount = userRepository.count();
        int userId = new Random().nextInt((int) usersCount) + 1;
        return userRepository.findById(userId);
    }
}
