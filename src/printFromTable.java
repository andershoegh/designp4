import java.sql.*;

public class printFromTable {

    public static void printTable(Connection conn) throws SQLException {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM testing");

            while (res.next()) {
                String id = res.getString("_id");
                String name = res.getString("name");
                String number = res.getString("number");
                System.out.println("ID på spilleren er: " + id + ". Navnet på spilleren er: " + name +
                        ". Telefon nummeret på spileren er: " + number);
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
