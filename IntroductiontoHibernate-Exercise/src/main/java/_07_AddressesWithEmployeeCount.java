import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class _07_AddressesWithEmployeeCount {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();


        List<Address> resultList = entityManager
                .createQuery("SELECT a FROM Address a" +
                             " ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();

        for (Address a : resultList) {
            String addressText = a.getText();
            String townName = a.getTown() == null ? "" : a.getTown().getName();
            int employeesCount = a.getEmployees().size();

            System.out.println(addressText + ", " + townName + " - " + employeesCount + " employees");
        }

        entityManager.getTransaction().commit();
    }
}
