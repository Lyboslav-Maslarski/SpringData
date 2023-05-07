package _02_SalesDatabase;

import _02_SalesDatabase.Entities.Customer;
import _02_SalesDatabase.Entities.Product;
import _02_SalesDatabase.Entities.Sale;
import _02_SalesDatabase.Entities.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Product product = new Product("Product", 10.0, BigDecimal.TEN);
        Customer customer = new Customer("Customer","email","creditCard");
        StoreLocation storeLocation = new StoreLocation("location");
        Sale sale = new Sale(product, customer, storeLocation);
        product.addSale(sale);
        customer.addSale(sale);
        storeLocation.addSale(sale);

        entityManager.persist(product);
        entityManager.persist(customer);
        entityManager.persist(storeLocation);
        entityManager.persist(sale);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
