package ProductShop.services;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface SeedService {
    void seedUser() throws FileNotFoundException, JAXBException;

    void seedCategories() throws FileNotFoundException, JAXBException;

    void seedProducts() throws FileNotFoundException, JAXBException;

    default void seedAll() throws FileNotFoundException, JAXBException {
        seedUser();
        seedCategories();
        seedProducts();
    }
}
