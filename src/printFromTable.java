import java.sql.*;

public class printFromTable {

    public static void printTable(Connection conn) throws SQLException {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM table");

            while (res.next()) {
                String playerName = res.getString("playerName");
                System.out.println(playerName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
