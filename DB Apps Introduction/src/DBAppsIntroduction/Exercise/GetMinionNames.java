package DBAppsIntroduction.Exercise;

import java.sql.*;
import java.util.Scanner;

public class GetMinionNames {
    public static void main(String[] args) throws SQLException {
        String user = "root";
        String password = "lybo9109";

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", user, password);

        PreparedStatement villainStatement = connection.prepareStatement("""
                SELECT\s
                    name
                FROM
                    villains
                WHERE
                    id = ?;""");

        PreparedStatement minionsStatement = connection.prepareStatement("""
                SELECT\s
                    m.name, m.age
                FROM
                    villains AS v
                        JOIN
                    minions_villains AS mv ON v.id = mv.villain_id
                        JOIN
                    minions AS m ON mv.minion_id=m.id
                WHERE
                    v.id = ?;""");

        Scanner scan = new Scanner(System.in);

        int villainID = scan.nextInt();

        villainStatement.setInt(1, villainID);

        ResultSet villainStatementResultSet = villainStatement.executeQuery();

        if (!villainStatementResultSet.next()) {
            System.out.println("No villain with ID " + villainID + " exists in the database.");
            return;
        }

        System.out.println("Villain: " + villainStatementResultSet.getString("name"));

        minionsStatement.setInt(1, villainID);

        ResultSet minionsStatementResultSet = minionsStatement.executeQuery();

        for (int i = 1; minionsStatementResultSet.next(); i++) {
            String minionName = minionsStatementResultSet.getString("m.name");
            String minionAge = minionsStatementResultSet.getString("m.age");
            System.out.println(i + ". " + minionName + " " + minionAge);
        }

        connection.close();
    }
}
