package example.services;

import java.io.FileNotFoundException;

public interface SeedService {
    void seedUser() throws FileNotFoundException;

    void seedCategories() throws FileNotFoundException;

    void seedProducts() throws FileNotFoundException;

    default void seedAll() throws FileNotFoundException {
        seedUser();
        seedCategories();
        seedProducts();
    }
}
