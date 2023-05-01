import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class _09_FindLatest10Projects {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Project> projects = entityManager
                .createQuery("SELECT p FROM Project p" +
                             " ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();

        projects.sort(Comparator.comparing(Project::getName));

        for (Project project : projects) {
            String projectName = project.getName();
            String projectDescription = project.getDescription();

            System.out.println("Project name: " + projectName);
            System.out.println("     Project Description: " + projectDescription);
            System.out.println("     Project Start Date: " + project.getStartDate().minusHours(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
            System.out.println("     Project End Date: " + project.getEndDate());

        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
