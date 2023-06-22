package DBAppsIntroduction.Exercise;

import java.sql.*;
import java.util.Scanner;

public class AddMinion {

    public static final String INSERT_INTO_TOWNS_NAME_VALUES = "INSERT INTO towns(name) VALUES (?);";
    public static final String INSERT_INTO_VILLAINS_NAME_EVILNESS_FACTOR_VALUES_EVIL = "INSERT INTO villains(name,evilness_factor) VALUES (?,'evil');";

    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", "root", "");

        Scanner scan = new Scanner(System.in);

        String[] minionData = scan.nextLine().split("\\s+");
        String minionName = minionData[1];
        int minionAge = Integer.parseInt(minionData[2]);
        String minionTown = minionData[3];

        String villainName = scan.nextLine().split("\\s+")[1];

        int townID = getId(connection, minionTown, """
                SELECT\s
                    id
                FROM
                    towns
                WHERE
                    name = ?;""", INSERT_INTO_TOWNS_NAME_VALUES);

        int villainID = getId(connection, villainName, """
                SELECT\s
                    id
                FROM
                    villains
                WHERE
                    name = ?;""", INSERT_INTO_VILLAINS_NAME_EVILNESS_FACTOR_VALUES_EVIL);

        PreparedStatement insertMinion = connection
                .prepareStatement("INSERT INTO minions(name,age,town_id) VALUES (?,?,?);");
        insertMinion.setString(1, minionName);
        insertMinion.setInt(2, minionAge);
        insertMinion.setInt(3, townID);
        insertMinion.executeUpdate();

        PreparedStatement getMinionID = connection
                .prepareStatement("SELECT id FROM minions ORDER BY id DESC LIMIT 1");
        ResultSet lastMinionSet = getMinionID.executeQuery();
        lastMinionSet.next();
        int minionID = lastMinionSet.getInt("id");

        PreparedStatement insertMinionToVillain = connection
                .prepareStatement("INSERT INTO minions_villains(minion_id,villain_id) VALUES (?,?);");
        insertMinionToVillain.setInt(1, minionID);
        insertMinionToVillain.setInt(2, villainID);
        insertMinionToVillain.executeUpdate();

        System.out.println("Successfully added " + minionName + " to be minion of " + villainName + ".");

        connection.close();
    }

    private static int getId(Connection connection, String name, String selectSQL, String updateSQL) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        int id;
        if (!resultSet.next()) {
            PreparedStatement insetTown = connection.prepareStatement(updateSQL);
            insetTown.setString(1, name);
            insetTown.executeUpdate();

            ResultSet newResultSet = preparedStatement.executeQuery();
            newResultSet.next();
            if (updateSQL.equals(INSERT_INTO_TOWNS_NAME_VALUES)) {
                System.out.println("Town " + name + " was added to the database.");
            } else {
                System.out.println("Villain " + name + " was added to the database.");
            }
            id = newResultSet.getInt("id");
        } else {
            id = resultSet.getInt("id");
        }
        return id;
    }
}
