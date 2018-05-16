package Training;

import Controller.DeleteAble;
import SQL.SqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program implements DeleteAble {

    private int id;
    private String name;
    private String description;
    private int numberOfExercises;
    private int duration;

    public Program(int id, String name, String description, int numberOfExercises, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfExercises = numberOfExercises;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfExercises() {
        return numberOfExercises;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void delete() {
        try{
            Connection conn = SqlConnection.connectToDB();

            String sql = "DELETE FROM programs WHERE program_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, this.id);
            statement.executeUpdate();

            SqlConnection.closeConnection();

        } catch (SQLException excp) {
            excp.printStackTrace();
        }
    }
}
