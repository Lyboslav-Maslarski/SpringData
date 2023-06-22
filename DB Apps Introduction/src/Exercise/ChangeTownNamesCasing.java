package DBAppsIntroduction.Exercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {
    public static void main(String[] args) throws SQLException {
        String user = "root";
        String password = "";

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", user, password);

        Scanner scan = new Scanner(System.in);
        String country = scan.nextLine();

        PreparedStatement updateTownNames = connection.prepareStatement("""
                UPDATE towns\s
                SET\s
                    name = UPPER(name)
                WHERE
                    country = ?;""");
        updateTownNames.setString(1, country);

        int townsChanged = updateTownNames.executeUpdate();

        if (townsChanged == 0) {
            System.out.println("No town names were affected.");
            return;
        }

        System.out.println(townsChanged + " town names were affected.");

        PreparedStatement selectTowns = connection.prepareStatement("""
                SELECT\s
                    name
                FROM
                    towns
                WHERE
                    country = ?;""");
        selectTowns.setString(1, country);

        ResultSet resultSet = selectTowns.executeQuery();

        List<String> towns = new ArrayList<>();
        while (resultSet.next()) {
            String townName = resultSet.getString("name");
            towns.add(townName);
        }
        System.out.println(towns);

        connection.close();
    }
}
