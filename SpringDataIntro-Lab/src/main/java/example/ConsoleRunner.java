package example;

import example.entities.Account;
import example.entities.User;
import example.services.AccountService;
import example.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final AccountService accountService;

    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Pesho", 25);
        Account account = new Account(BigDecimal.valueOf(10000L), user1);


//        userService.registerUser(user1);
//        accountService.registerAccount(account);
        accountService.withdrawMoney(BigDecimal.valueOf(50L), 1L);
    }
}
