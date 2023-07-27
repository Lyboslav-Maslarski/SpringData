package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dtos.CarExportDTO;
import softuni.exam.models.entities.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByMakeAndModelAndKilometers(String make, String model, Integer kilometers);

    @Query(value = "SELECT \n" +
            "    c.make,\n" +
            "    c.model,\n" +
            "    c.kilometers,\n" +
            "    c.registered_on,\n" +
            "    (SELECT \n" +
            "            COUNT(p.id)\n" +
            "        FROM\n" +
            "            pictures AS p\n" +
            "        WHERE\n" +
            "            p.car_id = c.id) AS picturesCount\n" +
            "FROM\n" +
            "    cars AS c\n" +
            "ORDER BY picturesCount DESC , c.make;",nativeQuery = true)
    List<CarExportDTO> findAllCars();
}
