package BookShopSystem.services;

import BookShopSystem.entities.Author;
import BookShopSystem.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getRandomAuthor() {
        long count = this.authorRepository.count();

        long authorId = new Random().nextInt((int) count) + 1L;

        return authorRepository.findById(authorId).orElse(null);
    }
}
