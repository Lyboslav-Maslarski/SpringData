package ProductShop;

import ProductShop.exportDTOS.CategoriesExportDTO;
import ProductShop.exportDTOS.ProductsInRangeDTO;
import ProductShop.exportDTOS.UsersWithSoldItemAndCountDTO;
import ProductShop.exportDTOS.UsersWithSoldItemDTO;
import ProductShop.services.CategoryService;
import ProductShop.services.ProductService;
import ProductShop.services.SeedService;
import ProductShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Component
public class ProductShopRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public ProductShopRunner(SeedService seedService, ProductService productService,
                             UserService userService, CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        /**
         * CHECK DB name in application.properties first
         */

//        this.seedService.seedAll();
//        this.queryOne();
//        this.queryTwo();
//        this.queryThree();
//        this.queryFour();
    }

    private void queryOne() throws JAXBException {
        ProductsInRangeDTO products = this.productService.getAllInRange(500, 1000);

        JAXBContext context = JAXBContext.newInstance(ProductsInRangeDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(products, System.out);
    }

    private void queryTwo() throws JAXBException {
        UsersWithSoldItemDTO users = this.userService.getAllWithSoldItem();

        JAXBContext context = JAXBContext.newInstance(UsersWithSoldItemDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(users, System.out);
    }

    private void queryThree() throws JAXBException {
        CategoriesExportDTO categories = this.productService.getCategoriesStats();

        JAXBContext context = JAXBContext.newInstance(CategoriesExportDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(categories, System.out);
    }

    private void queryFour() throws JAXBException {
        UsersWithSoldItemAndCountDTO users = this.userService.getAllWithSoldItemAndCount();

        JAXBContext context = JAXBContext.newInstance(UsersWithSoldItemAndCountDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(users, System.out);
    }
}
