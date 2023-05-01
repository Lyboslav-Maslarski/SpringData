package example.service;


import example.model.entity.AgeRestriction;
import example.model.entity.Book;
import example.model.entity.EditionType;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> bookTitlesByAgeRestriction(AgeRestriction ageRestriction);

    long getBooksCount();

    List<String> goldenBooks(EditionType type, int copies);

    List<String> booksByPrice(float lowerBound, float upperBound);

    List<String> notReleasedBook(String year);

    List<String> booksReleasedBeforeDate(String date);

    List<String> booksSearch(String string);

    List<String> bookTitleSearch(String string);

    int countBooks(int length);

    String reducedBook(String title);

    int increaseBookCopies(String dateString, int copies);

    int removeBooks(int copies);
}
