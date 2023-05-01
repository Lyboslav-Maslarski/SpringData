package example.service;


import example.model.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<String> authorsSearch(String string);

    List<String> totalBookCopies();

    String storedProcedure(String authorName);
}