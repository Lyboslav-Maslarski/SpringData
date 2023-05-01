import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class _06_AddingNewAddressAndUpdatingEmployee {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scan = new Scanner(System.in);
        String lastName = scan.nextLine();

        String addressText = "Vitoshka 15";
        Address address = new Address();
        address.setText(addressText);
        entityManager.persist(address);

//        Employee employee = entityManager
//                .createQuery("SELECT e FROM Employee e WHERE e.lastName = :lastName", Employee.class)
//                .setParameter("lastName", lastName)
//                .getSingleResult();
//        employee.setAddress(address);
//        entityManager.persist(employee);

        entityManager
                .createQuery("UPDATE Employee e " +
                             " SET e.address = :address " +
                             " WHERE e.lastName = :lastName")
                .setParameter("lastName", lastName)
                .setParameter("address", address)
                .executeUpdate();

        entityManager.getTransaction().commit();
    }
}
