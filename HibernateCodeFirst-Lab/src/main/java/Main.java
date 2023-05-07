import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        PlateNumber plateNumber = new PlateNumber("CB 4555 CB");
        Company company = new Company("BulgariaAir");
        Driver driver=new Driver("Nick Jonas");
        Car car = new Car("CAR", "NEW", BigDecimal.valueOf(1500), "disel", 4, plateNumber);
        Truck truck = new Truck("TRUCK", "NEW", BigDecimal.valueOf(7500), "disel", 100000.0);
        Bike bike = new Bike("BIKE", "NEW", BigDecimal.valueOf(150), "man");
        Plane plane = new Plane("PLANE", "NEW", BigDecimal.valueOf(150000), "disel", 400, company);

        driver.addTrucks(truck);
        truck.addDriver(driver);


        entityManager.persist(driver);
        entityManager.persist(car);
        entityManager.persist(truck);
        entityManager.persist(bike);
        entityManager.persist(plane);
        entityManager.persist(company);
        entityManager.persist(plateNumber);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
