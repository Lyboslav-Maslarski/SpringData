package DBAppsIntroduction.Lab;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DiabloDB {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String user = "root";
        String password = "lybo9109";

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", properties);

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT \n" +
                                  "    user_name, first_name, last_name, COUNT(u.id) AS 'games'\n" +
                                  "FROM\n" +
                                  "    users AS u\n" +
                                  "        JOIN\n" +
                                  "    users_games AS ug ON u.id = ug.user_id\n" +
                                  "WHERE\n" +
                                  "    user_name = ?\n" +
                                  "GROUP BY u.id;");

        String userName = sc.nextLine();
        preparedStatement.setString(1, userName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            System.out.printf("User: %s%n%s %s has played %d games",
                    resultSet.getString("user_name"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("games"));
        } else {
            System.out.println("No such user exists");
        }
        connection.close();
    }
}
