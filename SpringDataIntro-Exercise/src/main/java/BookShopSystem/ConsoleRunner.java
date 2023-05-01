package BookShopSystem;

import BookShopSystem.entities.Author;
import BookShopSystem.entities.Book;
import BookShopSystem.repositories.AuthorRepository;
import BookShopSystem.repositories.BookRepository;
import BookShopSystem.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        this.seedService.seedAuthors();
//        this.seedService.seedCategories();
//        this.seedService.seedBooks();
//        this.seedService.seedAll();

//        _01_booksAfter2000();

//        _02_authorsWithAtLeastOneBookBefore1990();

//        _03_allAuthorsOrderedByBookCount();

//        _04_allBooksFromAuthorOrderByReleaseDateThenByBookTitle();
    }

    private void _04_allBooksFromAuthorOrderByReleaseDateThenByBookTitle() {
        String firstName = "George";
        String lastName = "Powell";

        Author author = authorRepository.findByFirstNameAndLastName(firstName, lastName);

        List<Book> books = bookRepository.findAllByAuthorOrderByReleaseDateDescTitleAsc(author);

        books.forEach(book -> System.out.println(book.getTitle() + " " + book.getReleaseDate() + " " + book.getCopies()));
    }

    private void _03_allAuthorsOrderedByBookCount() {
        List<Author> authors = authorRepository.findAll();

        authors.sort((l, r) -> r.getBooks().size() - l.getBooks().size());

        authors.forEach(author -> System.out.println(author.getFirstName() + " " + author.getLastName() + " " + author.getBooks().size()));
    }

    private void _02_authorsWithAtLeastOneBookBefore1990() {
        LocalDate date = LocalDate.of(1990, 1, 1);

//        List<Book> books = bookRepository.findByReleaseDateBefore(date);
//
//        Set<Author> authors = new HashSet<>();
//        books.forEach(book -> authors.add(book.getAuthor()));
        List<Author> authors = authorRepository.findDistinctByBooksReleaseDateBefore(date);

        authors.forEach(author -> System.out.println(author.getFirstName() + " " + author.getLastName()));
    }

    private void _01_booksAfter2000() {
        LocalDate date = LocalDate.of(2000, 12, 31);

        List<Book> books = bookRepository.findByReleaseDateAfter(date);

        books.forEach(book -> System.out.println(book.getTitle()));
    }
}
