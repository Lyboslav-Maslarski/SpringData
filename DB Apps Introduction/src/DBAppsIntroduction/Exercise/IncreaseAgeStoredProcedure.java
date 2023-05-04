package DBAppsIntroduction.Exercise;

import java.sql.*;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "lybo9109");
        Scanner scan = new Scanner(System.in);
        PreparedStatement storedProcedureCreateStatement = connection.prepareStatement("""
                DELIMITER && \s
                CREATE PROCEDURE usp_get_older (minion_id INT) \s
                BEGIN \s
                    UPDATE minions SET age = age+1 WHERE id= minion_id;   \s
                END && \s
                DELIMITER ;\s""");

        int minionID = scan.nextInt();
        CallableStatement storedProcedureStatement = connection.prepareCall("CALL usp_get_older (?)");
        storedProcedureStatement.setInt(1, minionID);
        storedProcedureStatement.executeUpdate();

        PreparedStatement getMinionStatement = connection.prepareStatement("""
                SELECT\s
                    name, age
                FROM
                    minions
                WHERE
                    id = ?;""");
        getMinionStatement.setInt(1, minionID);
        ResultSet minionSet = getMinionStatement.executeQuery();
        if (minionSet.next()) {
            String minionName = minionSet.getString("name");
            int minionAge = minionSet.getInt("age");

            System.out.println(minionName + " " + minionAge);
        }

        connection.close();
    }
}
