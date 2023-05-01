import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class _10_IncreaseSalaries {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        List<String> departments = List.of("Engineering", "Tool Design", "Marketing", "Information Services");

        entityManager
                .createQuery("SELECT e FROM Employee e" +
                             " WHERE e.department.name IN (:departments)", Employee.class)
                .setParameter("departments", departments)
                .getResultList().forEach(e -> {
                    e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
                    entityManager.persist(e);

                    String firstName = e.getFirstName();
                    String lastName = e.getLastName();
                    BigDecimal salary = e.getSalary();

                    System.out.println(firstName + " " + lastName + " (" + salary + ")");
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
