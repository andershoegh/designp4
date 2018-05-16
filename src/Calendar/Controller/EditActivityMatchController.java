package Calendar.Controller;

import Match.Match;
import SQL.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class EditActivityMatchController {

    @FXML private DatePicker dateInput;
    @FXML private TextField startTimeInput;
    @FXML private TextField endTimeInput;
    @FXML private Button editButton;

    public void initData(Match match){

    }

    public void editButtonClick(){
        String sql = "INSERT INTO trainings" + "(training_id," +
                "date, start_time, end_time, program_id)" + "VALUES (null, ?, ?, ?, null)";
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            stmt.setString(2, startTimeInput.getText());
            stmt.setString(3, endTimeInput.getText());

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}