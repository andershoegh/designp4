package Other_Event;

import Controller.DeleteAble;
import SQL.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Other implements DeleteAble{

    private int id;
    private String name;
    private String date;
    private String time;

    public Other(int id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getId() { return id; }


    @Override
    public void delete() {

        try {
            Connection conn = SqlConnection.connectToDB();

            String sql = "DELETE FROM otherEvents WHERE other_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, this.id);
            statement.executeUpdate();

            SqlConnection.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
