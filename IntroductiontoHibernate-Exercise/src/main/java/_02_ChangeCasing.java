import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class _02_ChangeCasing {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT t FROM Town t", Town.class);
        List<Town> towns = query.getResultList();

        for (Town town : towns) {
            String name = town.getName();
            if (name.length() <= 5) {
                town.setName(name.toUpperCase());
                entityManager.persist(town);
            }
        }

        entityManager.getTransaction().commit();
    }
}
