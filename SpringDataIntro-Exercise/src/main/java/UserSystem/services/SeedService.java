package UserSystem.services;

import java.io.IOException;

public interface SeedService {
    void seedTownsAndCountries() throws IOException;
    void seedUsers() throws IOException;
    default void seedAll() throws IOException {
        seedTownsAndCountries();
        seedUsers();
    }
}
