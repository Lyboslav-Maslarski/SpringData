package DBAppsIntroduction.Lab;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class SoftUniDB {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String user = "root";
        String password =  "lybo9109";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/soft_uni", props);

        PreparedStatement stmt =
                connection.prepareStatement("SELECT * FROM employees WHERE salary > ?");

        double salary = Double.parseDouble(sc.nextLine());
        stmt.setDouble(1, salary);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
        }
        connection.close();
    }
}