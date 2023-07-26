package hiberspring.repository;

import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByCardNumber(String number);

    @Query(value = "SELECT\n" +
            "    * \n" +
            "FROM\n" +
            "    employees AS e\n" +
            "        JOIN\n" +
            "    branches AS b ON e.branch_id = b.id\n" +
            "        JOIN\n" +
            "    products AS p ON b.id = p.branch_id",nativeQuery = true)
    Set<Employee> findAllWorkingInBranchWithAtLeastOneProduct();
}
