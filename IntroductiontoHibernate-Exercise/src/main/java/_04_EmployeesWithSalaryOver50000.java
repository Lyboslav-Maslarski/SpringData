import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class _04_EmployeesWithSalaryOver50000 {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT e.firstName FROM Employee e WHERE salary > 50000", String.class);

        List<String> resultList = query.getResultList();

        for (String name : resultList) {
            System.out.println(name);
        }

        entityManager.getTransaction().commit();
    }
}
