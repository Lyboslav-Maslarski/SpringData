package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;

import java.util.List;
import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {

    Optional<Star> findByName(String name);

    @Query(value = "SELECT \n" +
            "    *\n" +
            "FROM\n" +
            "    stars\n" +
            "WHERE\n" +
            "    star_type = 'RED_GIANT'\n" +
            "        AND id NOT IN (SELECT \n" +
            "            observing_star_id\n" +
            "        FROM\n" +
            "            astronomers)\n" +
            "ORDER BY light_years", nativeQuery = true)
    List<Star> findAllByStarTypeOrderByLightYearsAsc(StarType starType);
    List<Star> findAllByStarTypeAndObserversIsEmptyOrderByLightYears(StarType starType);
}
