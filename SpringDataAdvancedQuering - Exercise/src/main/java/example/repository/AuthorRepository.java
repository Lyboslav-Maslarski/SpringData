package example.repository;

import example.model.entity.Author;
import example.model.entity.AuthorWithCopies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findByFirstNameEndingWith(String string);

    @Query("SELECT a.firstName AS firstName," +
           " a.lastName AS lastName," +
           " SUM(b.copies) AS totalCopies" +
           " FROM Author a" +
           " JOIN a.books b" +
           " GROUP BY b.author" +
           " ORDER BY totalCopies DESC")
    List<AuthorWithCopies> getAllAuthorsWithTotalBookCopies();

    @Procedure("udp_find_books_by_author")
    int getAuthorBookCount(@Param("first_name") String firstName, @Param("last_name") String lastName);
}
