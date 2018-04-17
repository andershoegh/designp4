package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InnerJoinDB {

    public static void extractFromMatchTacticPlayer(String table, String column){
        String tableId = table.contains("matches") ? table.substring(0, table.length() - 2) : table.substring(0, table.length() - 1);
        String query = "SELECT *, " + table + "." + column + " FROM match_tactic_player INNER JOIN " + table + " ON match_tactic_player." + tableId + "_id = " + table + "." + tableId + "_id";

        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String player_name = rs.getString(column);
                System.out.println(player_name);
            }

            SqlConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
