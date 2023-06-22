package DBAppsIntroduction.Exercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "");

        PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT\s
                    name
                FROM
                    minions;""");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> minionsNames = new ArrayList<>();
        while (resultSet.next()) {
            String currentMinionName = resultSet.getString("name");
            minionsNames.add(currentMinionName);
        }

        for (int i = 0; i < minionsNames.size() / 2; i++) {
            System.out.println(minionsNames.get(i));
            System.out.println(minionsNames.get(minionsNames.size() - 1 - i));
        }

        connection.close();
    }
}
