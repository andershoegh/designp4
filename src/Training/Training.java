package Training;

import Controller.DeleteAble;
import SQL.SqlConnection;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Training implements DeleteAble {

    private int id;
    private String date;
    private String startTime;
    private String endTime;
    private int programID;

    public Training(int id, String date, String startTime, String endTime, int programID) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.programID = programID;
    }


    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getProgramID() {
        return programID;
    }

    @Override
    public void delete() {

        try {
            Connection conn = SqlConnection.connectToDB();

            String sql = "DELETE FROM trainings WHERE training_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, this.id);
            statement.executeUpdate();

            SqlConnection.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
