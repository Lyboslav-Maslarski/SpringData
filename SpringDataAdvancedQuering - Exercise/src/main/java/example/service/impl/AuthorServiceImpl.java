package example.service.impl;


import example.model.entity.Author;
import example.repository.AuthorRepository;
import example.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(AUTHORS_FILE_PATH))
                .forEach(row -> {
                    String[] fullName = row.split("\\s+");
                    Author author = new Author(fullName[0], fullName[1]);

                    authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1,
                        authorRepository.count() + 1);

        return authorRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public List<String> getAllAuthorsOrderByCountOfTheirBooks() {
        return authorRepository
                .findAllByBooksSizeDESC()
                .stream()
                .map(author -> String.format("%s %s %d",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> authorsSearch(String string) {
        return authorRepository.findByFirstNameEndingWith(string)
                .stream()
                .map(a -> a.getFirstName() + " " + a.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> totalBookCopies() {
        return authorRepository.getAllAuthorsWithTotalBookCopies()
                .stream()
                .map(a -> a.getFirstName() + " " + a.getLastName() + " - " + a.getTotalCopies())
                .collect(Collectors.toList());
    }

    @Override
    public String storedProcedure(String authorName) {
        String firstName = authorName.split(" ")[0];
        String lastName = authorName.split(" ")[1];
        int authorBookCount = authorRepository.getAuthorBookCount(firstName, lastName);
        return authorName + " has written " + authorBookCount + " books";
    }
}
