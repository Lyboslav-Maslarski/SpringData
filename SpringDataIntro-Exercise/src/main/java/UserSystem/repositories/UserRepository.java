package UserSystem.repositories;

import UserSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByEmailEndingWith(String emailProvider);

    List<User> findByLastTimeLoggedInIsBefore(LocalDate searchedDate);

    @Transactional
    void deleteAllByIsDeleted(boolean bool);
}
