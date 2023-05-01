import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;

public class _08_GetEmployeeWithProject {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scan = new Scanner(System.in);
        int id = scan.nextInt();

        Employee employee = entityManager
                .createQuery("SELECT e FROM Employee e" +
                             " WHERE id = ?1", Employee.class)
                .setParameter(1, id)
                .getSingleResult();


        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        String departmentName = employee.getJobTitle();
        Set<Project> projects = employee.getProjects();

        System.out.println(firstName + " " + lastName + " - " + departmentName);
        projects.stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> System.out.println("      " + project.getName()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
