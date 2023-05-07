package _04_HospitalDatabase;

import _04_HospitalDatabase.core.Controller;
import _04_HospitalDatabase.core.ControllerImpl;
import _04_HospitalDatabase.core.Engine;
import _04_HospitalDatabase.core.EngineImp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        Controller controller = new ControllerImpl(scanner, entityManager);

        Engine engine = new EngineImp(scanner,controller);

        engine.run();

        entityManager.close();
    }
}
