package SQL;

import java.sql.*;

public class SqlConnection {

    static Connection conn = null;

    public static Connection connectToDB(){

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:coachDB.db");
            if (conn != null) {
                System.out.println("--- Connected to coachDB ---");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public static void closeConnection(){

        try {
            conn.close();
            System.out.println("--- Connection closed ---");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String getTeamNameFromDB() {

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT team_name FROM team");
            ResultSet rs = statement.executeQuery();
            return rs.getString("team_name");
        } catch (SQLException e) {
            System.out.println("Could not connect to team table " + e.getMessage());
        }
        finally {
            closeConnection();
        }

        return null;
    }
}
