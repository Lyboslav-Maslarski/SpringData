import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class _03_ContainsEmployee {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();

        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        String firstName = name.split("\\s+")[0];
        String lastName = name.split("\\s+")[1];

        List<Employee> result = manager
                .createQuery("SELECT e FROM Employee e WHERE firstName = :firstName AND lastName = :lastName", Employee.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();

        manager.getTransaction().commit();
        if (result.isEmpty()) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }
    }
}
