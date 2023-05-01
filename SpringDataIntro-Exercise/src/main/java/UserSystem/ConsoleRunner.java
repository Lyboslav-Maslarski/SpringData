package UserSystem;

import UserSystem.entities.User;
import UserSystem.repositories.UserRepository;
import UserSystem.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final UserRepository userRepository;
    private final Scanner scanner;

    @Autowired
    public ConsoleRunner(SeedService seedService, UserRepository userRepository) {
        this.seedService = seedService;
        this.userRepository = userRepository;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedAll();
        this._01_findByEmailProvider();
        this._02_removeInactiveUsers();
    }

    private void _02_removeInactiveUsers() {
        System.out.println("Please fill in date (date format year/month(1-12)/d):");

        String[] datePart = new Scanner(System.in).nextLine().split("/");

        LocalDate searchedDate =
                LocalDate.of(Integer.parseInt(datePart[0]), Integer.parseInt(datePart[1]), Integer.parseInt(datePart[2]));

        List<User> users = userRepository.findByLastTimeLoggedInIsBefore(searchedDate);

        users.forEach(u -> {
            u.setDeleted(true);
            userRepository.save(u);
        });
        System.out.printf("%d inactive users marked to be deleted.%n", users.size());

        userRepository.deleteAllByIsDeleted(true);
    }

    private void _01_findByEmailProvider() {
        System.out.println("Please fill e-mail provider:");

        String emailProvider = scanner.nextLine();

        List<User> users = userRepository.findAllByEmailEndingWith(emailProvider);

        if (users.isEmpty()) {
            System.out.println("There are no users with email from this provider.");
            return;
        }

        users.forEach(u -> System.out.printf("User: %s, with username: %s, has email: %s%n",
                u.getFullName(), u.getUsername(), u.getEmail()));
    }
}
