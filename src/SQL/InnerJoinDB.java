package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InnerJoinDB {

    public static ResultSet extractDataFromForeign(String table, String foreignTable, String column){
        ResultSet rs = null;

        String tableId = table.contains("matches") ? table.substring(0, table.length() - 2) : table.substring(0, table.length() - 1);

        String query = "SELECT *, " + table + "." + column + " FROM " + foreignTable + " INNER JOIN " +
                table + " ON " + foreignTable + "." + tableId + "_id = " + table + "." + tableId + "_id";

        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement(query);
            rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;

        // SqlConnection.closeConnection() skal køres efter kald af extractDataFromForeign() og behandling af ResultSet
    }
}
