import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class _05_EmployeesFromDepartment {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        String department = "Research and Development";
        Query query = entityManager
                .createQuery("SELECT e FROM Employee e " +
                             " WHERE e.department.name = :departmentName" +
                             " ORDER BY e.salary ASC, e.id ASC", Employee.class)
                .setParameter("departmentName", department);

        List<Employee> resultList = query.getResultList();

        for (Employee e : resultList) {
            System.out.println(e.getFirstName() + " " + e.getLastName() + " from Research and Development - $" + e.getSalary());
        }

        entityManager.getTransaction().commit();
    }
}
