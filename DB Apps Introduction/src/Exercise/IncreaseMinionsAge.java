package DBAppsIntroduction.Exercise;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "");
        Scanner scan = new Scanner(System.in);

        List<Integer> minionsToChangeIDs = Arrays.stream(scan.nextLine().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());

        PreparedStatement updateMinionsStatement = connection.prepareStatement("""
                UPDATE minions\s
                SET\s
                    name = LOWER(name),
                    age = age + 1
                WHERE
                    id = ?;""");

        for (Integer currentID : minionsToChangeIDs) {
            updateMinionsStatement.setInt(1, currentID);
            updateMinionsStatement.executeUpdate();
        }

        PreparedStatement selectAllMinionsStatement = connection.prepareStatement("""
                SELECT\s
                    name,age
                FROM
                    minions;""");

        ResultSet allMinionsSet = selectAllMinionsStatement.executeQuery();
        while (allMinionsSet.next()) {
            String name = allMinionsSet.getString("name");
            int age = allMinionsSet.getInt("age");

            System.out.println(name + " " + age);
        }

        connection.close();
    }
}
