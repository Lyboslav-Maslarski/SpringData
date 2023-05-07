package _05_BillsPaymentSystem;

import _05_BillsPaymentSystem.Entities.BankAccount;
import _05_BillsPaymentSystem.Entities.BillingDetail;
import _05_BillsPaymentSystem.Entities.CreditCard;
import _05_BillsPaymentSystem.Entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.Month;
import java.time.Year;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = new User("petar", "petrov", "pesho123");
        BillingDetail billingDetail1 = new CreditCard("1234", "Mastercard", Month.APRIL, Year.now());
        BillingDetail billingDetail2 = new BankAccount("1234", "bank-name","123");

        billingDetail1.setOwner(user);
        user.addBillingDetail(billingDetail1);

        entityManager.persist(billingDetail1);
        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
