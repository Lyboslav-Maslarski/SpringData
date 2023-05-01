package BookShopSystem.repositories;

import BookShopSystem.entities.Author;
import BookShopSystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByReleaseDateAfter(LocalDate date);

    List<Book> findByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAuthor(Author author);

    List<Book> findAllByAuthorOrderByReleaseDateDescTitleAsc(Author author);

}
