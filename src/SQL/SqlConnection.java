package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
