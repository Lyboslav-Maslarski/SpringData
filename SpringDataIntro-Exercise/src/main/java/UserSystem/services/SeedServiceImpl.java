package UserSystem.services;

import UserSystem.entities.Country;
import UserSystem.entities.Town;
import UserSystem.entities.User;
import UserSystem.repositories.CountryRepository;
import UserSystem.repositories.TownRepository;
import UserSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SeedServiceImpl implements SeedService {
    private static final String RESOURCE_PATH = "src\\main\\resources\\files";
    private static final String TOWNS_FILE_NAME = RESOURCE_PATH + "\\towns.txt";
    private static final String USERS_FILE_NAME = RESOURCE_PATH + "\\users.txt";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TownRepository townRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void seedTownsAndCountries() throws IOException {
        Files.readAllLines(Path.of(TOWNS_FILE_NAME))
                .stream()
                .map(s -> s.split(" "))
                .forEach(arr -> {
                    Country country = countryRepository.findByName(arr[1]);
                    if (country == null) {
                         country = new Country(arr[1]);
                        countryRepository.save(country);
                    }
                    Town town = townRepository.findByName(arr[0]);
                    if (town == null) {
                        town = new Town(arr[0]);
                        town.setCountry(country);
                        townRepository.save(town);
                    }

                });
    }

    @Override
    public void seedUsers() throws IOException {
        Files.readAllLines(Path.of(USERS_FILE_NAME))
                .stream()
                .filter(s -> !s.isBlank())
                .map(this::getUser)
                .forEach(userRepository::save);
    }

    private User getUser(String line) {
        String[] userInfo = line.split(" ");

        String firstName = userInfo[0];
        String lastName = userInfo[1];
        String username = userInfo[2];
        String password = userInfo[3];
        String email = userInfo[4];
        short age = Short.parseShort(userInfo[5]);

        String bornTownName = userInfo[6];
        Town bornTown = townRepository.findByName(bornTownName);

        String currentTownName = userInfo[7];
        Town currentTown = townRepository.findByName(currentTownName);

        LocalDate dateRegister = LocalDate.parse(userInfo[8], DateTimeFormatter.ofPattern("d/M/yyyy"));

        return new User(firstName, lastName, username, password, email, dateRegister, LocalDate.now(), age, bornTown, currentTown);
    }
}
