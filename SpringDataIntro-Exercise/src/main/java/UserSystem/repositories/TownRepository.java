package UserSystem.repositories;

import UserSystem.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town,Integer> {
    Town findByName(String townName);
}
