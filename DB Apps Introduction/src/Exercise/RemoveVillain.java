package DBAppsIntroduction.Exercise;

import java.sql.*;
import java.util.Scanner;

public class RemoveVillain {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);

        int villainID = scan.nextInt();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "");

        PreparedStatement selectVillainStatement = connection.prepareStatement("""
                SELECT\s
                    name
                FROM
                    villains
                WHERE
                    id = ?;""");
        selectVillainStatement.setInt(1, villainID);
        ResultSet villainSet = selectVillainStatement.executeQuery();

        if (!villainSet.next()) {
            System.out.println("No such villain was found");
            return;
        }
        String villainName = villainSet.getString("name");

        try {
            connection.setAutoCommit(false);

            PreparedStatement deleteMinionsStatement = connection.prepareStatement("""
                    DELETE FROM minions_villains\s
                    WHERE
                        villain_id = ?;""");
            deleteMinionsStatement.setInt(1, villainID);
            int minionsDeleted = deleteMinionsStatement.executeUpdate();

            PreparedStatement deleteVillain = connection.prepareStatement("""
                    DELETE FROM villains\s
                    WHERE
                        id = ?;""");
            deleteVillain.setInt(1, villainID);
            deleteVillain.executeUpdate();

            connection.commit();

            System.out.println(villainName + " was deleted");
            System.out.println(minionsDeleted + " minions released");

        } catch (SQLException e) {
            connection.rollback();
        }

        connection.close();
    }
}
