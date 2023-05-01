package example;

import example.model.entity.AgeRestriction;
import example.model.entity.EditionType;
import example.service.AuthorService;
import example.service.BookService;
import example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bookService.getBooksCount() == 0) {
            seedData();
        }
        System.out.println("==========01. Books Titles by Age Restriction==========");
        System.out.println("Enter age restriction (minor, teen, adult): ");
        AgeRestriction ageRestriction = AgeRestriction.valueOf(scanner.nextLine().toUpperCase());
        bookService.bookTitlesByAgeRestriction(ageRestriction).forEach(System.out::println);
        System.out.println("==========02. Golden Books==========");
        bookService.goldenBooks(EditionType.GOLD,5000).forEach(System.out::println);
        System.out.println("==========03. Books by Price==========");
        bookService.booksByPrice(5,40).forEach(System.out::println);
        System.out.println("==========04. Not Released Books==========");
        bookService.notReleasedBook("2000").forEach(System.out::println);
        bookService.notReleasedBook("1998").forEach(System.out::println);
        System.out.println("==========05. Books Released Before Date==========");
        bookService.booksReleasedBeforeDate("12-04-1992").forEach(System.out::println);
        bookService.booksReleasedBeforeDate("30-12-1989").forEach(System.out::println);
        System.out.println("==========06. Authors Search==========");
        authorService.authorsSearch("e").forEach(System.out::println);
        authorService.authorsSearch("dy").forEach(System.out::println);
        System.out.println("==========07. Books Search==========");
        bookService.booksSearch("sK").forEach(System.out::println);
        bookService.booksSearch("WOR").forEach(System.out::println);
        System.out.println("==========08. Book Titles Search==========");
        bookService.bookTitleSearch("Ric").forEach(System.out::println);
        bookService.bookTitleSearch("gr").forEach(System.out::println);
        System.out.println("==========09. Count Books==========");
        int booksCount1 = bookService.countBooks(12);
        System.out.println("There are " + booksCount1 + " books with longer title than 12 symbols");
        int booksCount2 = bookService.countBooks(40);
        System.out.println("There are " + booksCount2 + " books with longer title than 40 symbols");
        System.out.println("==========10. Total Book Copies==========");
        authorService.totalBookCopies().forEach(System.out::println);
        System.out.println("==========11. Reduced Book==========");
        System.out.println(bookService.reducedBook("Things Fall Apart"));
        System.out.println("==========12. Increase Book Copies==========");
        String date1 = "12 Oct 2005";
        int copies1 = 100;
        int bookCount1 = bookService.increaseBookCopies(date1, copies1);
        System.out.printf("%d books are released after %s, so total of %d book copies were added%n", bookCount1, date1, bookCount1 * copies1);
        int copies2 = 44;
        String date2 = "06 Jun 2013";
        int bookCount2 = bookService.increaseBookCopies(date2, copies2);
        System.out.printf("%d books are released after %s, so total of %d book copies were added%n", bookCount2, date2, bookCount2 * copies2);
        System.out.println("==========13. Remove Books==========");
        System.out.println(bookService.removeBooks(206));
        System.out.println("==========14. Stored Procedure==========");
        System.out.println(authorService.storedProcedure("Amanda Rice"));
        System.out.println(authorService.storedProcedure("Christina Jordan"));
        System.out.println(authorService.storedProcedure("Wanda Morales"));

    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
