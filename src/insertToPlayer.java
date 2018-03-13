import java.sql.*;

/**
 * JdbcInsert1.java - Demonstrates how to INSERT data into an SQL
 *                    database using Java JDBC.
 */
class insertToPlayer {

    public static void insertPlayers(Connection conn) throws SQLException {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO player " +
                    "VALUES (null, 'Test', '9000 Aalborg', 31313131," +
                    "'test@test.dk', null, null, 'Bænken', 1,0,0,0,0,0,0,0)");

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}