package example;

import example.exceptions.IllegalAccessException;
import example.exceptions.ValidationException;
import example.services.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {


    private final ExecutorService executorService;

    @Autowired
    public ConsoleRunner(ExecutorService executorService) {
        this.executorService = executorService;
    }


    @Override
    public void run(String... args) {
        Scanner scan = new Scanner(System.in);

        String commandLine = scan.nextLine();

        while (!"end".equalsIgnoreCase(commandLine)) {

            String result;
            try {
                result = executorService.execute(commandLine);
            } catch (ValidationException | IllegalAccessException | IllegalArgumentException e) {
                result = e.getMessage();
            }
            System.out.println(result);

            commandLine = scan.nextLine();
        }
    }
}
