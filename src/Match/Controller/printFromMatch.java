package Match.Controller;

import java.sql.*;

public class printFromMatch {

    public static void printMatch(Connection conn) throws SQLException {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM matches");

            while (res.next()) {
                String id = res.getString("match_id");
                String opponent = res.getString("opponent");
                String goalsFor = res.getString("goalsFor");
                String goalsAgainst = res.getString("goalsAgainst");
                String season = res.getString("season");
                String date = res.getString("date");
                String time = res.getString("time");
                System.out.println(id + " " + opponent + " " + goalsFor + " " + goalsAgainst + " " + season + " " + date + " " + time);
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
