package Calendar.Controller;

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

public class CreateActivityTrainingController {

    @FXML private DatePicker dateInput;
    @FXML private TextField startTimeInput;
    @FXML private TextField endTimeInput;
    @FXML private Button createButton;

    public void createButtonClick(){
        String sql = "INSERT INTO trainings" + "(training_id," +
                "date, start_time, end_time, program_id)" + "VALUES (null, ?, ?, ?, null)";
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setString(1, date);

            stmt.setString(2, startTimeInput.getText());

            stmt.setString(3, endTimeInput.getText());

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
