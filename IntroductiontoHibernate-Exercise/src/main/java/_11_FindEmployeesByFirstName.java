import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class _11_FindEmployeesByFirstName {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scan = new Scanner(System.in);
        String pattern = scan.nextLine();

        entityManager
                .createQuery("SELECT e FROM Employee e" +
                             " WHERE e.firstName LIKE :pattern", Employee.class)
                .setParameter("pattern", pattern+"%")
                .getResultList().forEach(e -> {

                    String firstName = e.getFirstName();
                    String lastName = e.getLastName();
                    String jobTitle = e.getJobTitle();
                    BigDecimal salary = e.getSalary();

                    System.out.printf("%s %s - %s - ($%.2f)%n", firstName, lastName, jobTitle, salary);
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
