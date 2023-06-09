package BookShopSystem.repositories;

import BookShopSystem.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    List<Author> findDistinctByBooksReleaseDateBefore(LocalDate date);

    Author findByFirstNameAndLastName(String firstName, String lastName);
}
