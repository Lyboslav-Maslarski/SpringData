import entities.Address;
import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        MyConnector.createConnection("root", "lybo9109", "custom_orm");
        Connection connection = MyConnector.getConnection();

        EntityManager<Address> entityManager = new EntityManager<>(connection);

        User user = new User("Pesho", 19, LocalDate.of(2022, 6, 20));
        Address address = new Address("Bulgaria", "Sofia", "Carevo selo", 5, "1000");

//        entityManager.doCreate(Address.class);
        entityManager.persist(address);

        connection.close();
    }
}
