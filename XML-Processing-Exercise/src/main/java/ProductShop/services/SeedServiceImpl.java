package ProductShop.services;

import ProductShop.entities.Category;
import ProductShop.entities.Product;
import ProductShop.entities.User;
import ProductShop.importDTOS.CategoriesImportDTO;
import ProductShop.importDTOS.ProductImportDTO;
import ProductShop.importDTOS.ProductsImportDTO;
import ProductShop.importDTOS.UsersImportDTO;
import ProductShop.repositories.CategoryRepository;
import ProductShop.repositories.ProductRepository;
import ProductShop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private static final String USERS_XML_PATH = "src/main/resources/import/ProductShop/users.xml";
    private static final String CATEGORIES_XML_PATH = "src/main/resources/import/ProductShop/categories.xml";
    private static final String PRODUCTS_XML_PATH = "src/main/resources/import/ProductShop/products.xml";
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;

        this.modelMapper = new ModelMapper();
    }

    @Override
    public void seedUser() throws FileNotFoundException, JAXBException {
        FileReader fileReader = new FileReader(USERS_XML_PATH);

        JAXBContext context = JAXBContext.newInstance(UsersImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        UsersImportDTO usersImportDTO = (UsersImportDTO) unmarshaller.unmarshal(fileReader);

        List<User> users = usersImportDTO.getUsers().stream()
                .map(u -> modelMapper.map(u, User.class))
                .collect(Collectors.toList());

        userRepository.saveAll(users);
    }

    @Override
    public void seedCategories() throws FileNotFoundException, JAXBException {
        FileReader fileReader = new FileReader(CATEGORIES_XML_PATH);

        JAXBContext context = JAXBContext.newInstance(CategoriesImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        CategoriesImportDTO categoriesImportDTO = (CategoriesImportDTO) unmarshaller.unmarshal(fileReader);

        List<Category> categories = categoriesImportDTO.getCategories().stream()
                .map(importDTO -> this.modelMapper.map(importDTO, Category.class))
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);
    }

    @Override
    public void seedProducts() throws FileNotFoundException, JAXBException {
        FileReader fileReader = new FileReader(PRODUCTS_XML_PATH);

        JAXBContext context = JAXBContext.newInstance(ProductsImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ProductsImportDTO productsImportDTO = (ProductsImportDTO) unmarshaller.unmarshal(fileReader);

        List<Product> products = productsImportDTO.getProducts().stream()
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
