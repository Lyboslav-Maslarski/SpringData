package DBAppsIntroduction.Exercise;

import java.sql.*;

public class GetVillainsNames {
    public static void main(String[] args) throws SQLException {
        String user = "root";
        String password = "";

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", user, password);

        PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT\s
                    name, COUNT(DISTINCT mv.minion_id) AS 'count'
                FROM
                    villains AS v
                        JOIN
                    minions_villains AS mv ON v.id = mv.villain_id
                GROUP BY v.id
                HAVING count > 15
                ORDER BY count DESC;""");

        preparedStatement.executeQuery();

        ResultSet resultSet = preparedStatement.getResultSet();

        while (resultSet.next()) {
            String villainName = resultSet.getString("name");
            int minionsCount = resultSet.getInt("count");

            System.out.println(villainName + " " + minionsCount);
        }

        connection.close();
    }
}
