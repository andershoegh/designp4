package SQL.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

//
//
// THIS SHIT WON'T WORK
//
//

public class print {
    public print(Connection conn) throws SQLException {

        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM player");

            while (res.next()) {
                String id = res.getString("_id");
                String name = res.getString("name");
                String address = res.getString("address");
                String phone = res.getString("phone");
                String mail = res.getString("mail");
                String iceName = res.getString("iceName");
                String iceTelephone = res.getString("iceTelephone");
                String position = res.getString("position");
                String health = res.getString("health");
                String yellowCards = res.getString("yellowCards");
                String redCards = res.getString("redCards");
                String goalScored = res.getString("goalScored");
                String assist = res.getString("assist");
                String motm = res.getString("motm");
                String attendedMatches = res.getString("attendedMatches");
                String attendedTrainings = res.getString("attendedTrainings");

                System.out.println(id + " " + name + " " +
                        address + " " + phone + " " +
                        mail + " " + iceName + " " +
                        iceTelephone + " " + position + " " +
                        health + " " + yellowCards + " " +
                        redCards + " " + goalScored + " " +
                        assist + " " + motm + " " +
                        attendedMatches + " " + attendedTrainings);
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());

        } finally {
            assert stmt != null;
            Objects.requireNonNull(stmt).close();
        }
        return print;
    }
}