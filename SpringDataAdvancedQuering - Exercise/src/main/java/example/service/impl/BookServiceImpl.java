package example.service.impl;


import example.model.entity.*;
import example.repository.BookRepository;
import example.service.AuthorService;
import example.service.BookService;
import example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> bookTitlesByAgeRestriction(AgeRestriction ageRestriction) {
        return bookRepository.findByAgeRestriction(ageRestriction);
    }

    @Override
    public long getBooksCount() {
        return bookRepository.count();
    }

    @Override
    public List<String> goldenBooks(EditionType type, int copies) {
        return bookRepository.findAllByEditionTypeAndCopiesLessThan(type, copies)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> booksByPrice(float lowerBound, float upperBound) {
        return bookRepository.findByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(lowerBound), BigDecimal.valueOf(upperBound))
                .stream()
                .map(b -> b.getTitle() + " - $" + b.getPrice())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> notReleasedBook(String year) {
        LocalDate before = LocalDate.of(Integer.parseInt(year), 1, 1);
        LocalDate after = LocalDate.of(Integer.parseInt(year), 12, 31);
        return bookRepository.findByReleaseDateBeforeOrReleaseDateAfter(before, after)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> booksReleasedBeforeDate(String date) {
        LocalDate releasedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return bookRepository.findByReleaseDateBefore(releasedDate)
                .stream()
                .map(b -> b.getTitle() + " " + b.getEditionType() + " " + b.getPrice())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> booksSearch(String string) {
        return bookRepository.findByTitleContaining(string)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> bookTitleSearch(String string) {
        return bookRepository.findByAuthorLastNameStartingWith(string)
                .stream()
                .map(b -> String.format("%s (%s %s)",
                        b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public int countBooks(int length) {
        return bookRepository.countBookWithTitleLongerThan(length);
    }

    @Override
    public String reducedBook(String title) {
        return bookRepository.getBookByTitle(title).String();
    }

    @Override
    public int increaseBookCopies(String dateString, int copies) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd MMM yyyy"));
        return bookRepository.increaseBookCopiesForBooksAfterReleasedDate(date, copies);
    }

    @Override
    public int removeBooks(int copies) {
        return bookRepository.deleteByCopiesLessThan(copies);
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
