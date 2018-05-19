package Training;

import Controller.DeleteAble;
import SQL.SqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program implements DeleteAble {
    //Fields for a training program
    private int id;
    private String name;
    private String notes;
    private String duration;
    private int numExercises;

    //Constructor
    public Program(int id, String name, String notes, String duration, int numExercises) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.duration = duration;
        this.numExercises = numExercises;
    }

    //Getters
    public int getId() {return id;}
    public String getName() {
        return name;
    }
    public String getNotes() {
        return notes;
    }
    public String getDuration() {
        return duration;
    }
    public int getNumExercises() {return numExercises;}

    //Implementing the delete-method from Deleteable interface
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
