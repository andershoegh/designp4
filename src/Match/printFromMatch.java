package Match;

import java.sql.*;

public class printFromMatch {

    public static void printMatch(Connection conn) throws SQLException {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM match");

            while (res.next()) {
                String id = res.getString("_id");
                String goalsFor = res.getString("goalsFor");
                String goalsAgainst = res.getString("goalsAgainst");
                String redCards = res.getString("redCards");
                String yellowCards = res.getString("yellowCards");
                String playerScored = res.getString("playerScored");
                String opponentName = res.getString("opponentName");
                System.out.println(id + " " + goalsFor + " " + goalsAgainst + " " + redCards + " " + yellowCards + " " + playerScored + " " + opponentName);
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
