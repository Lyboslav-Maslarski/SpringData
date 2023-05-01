import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class _13_RemoveTowns {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scan = new Scanner(System.in);
        String townName = scan.nextLine();

        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a WHERE a.town.name = :townName", Address.class)
                .setParameter("townName", townName)
                .getResultList();

        int deletedAddresses = addresses.size();

        addresses.forEach(address -> {
            address.getEmployees().forEach(employee -> employee.setAddress(null));
            entityManager.remove(address);
        });

        Town town = entityManager
                .createQuery("SELECT t FROM Town t WHERE t.name = :townName", Town.class)
                .setParameter("townName", townName)
                .getSingleResult();

        entityManager.remove(town);

        System.out.printf("%d %s in %s deleted", deletedAddresses, deletedAddresses == 1 ? "address" : "addresses", townName);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
