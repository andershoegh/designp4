package Calendar.Controller;

import SQL.SqlConnection;
import Season.Season;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class CreateActivityMiscController {

    @FXML private TextField nameInputField;
    @FXML private DatePicker dateInput;
    @FXML private TextField timeInput;
    @FXML private TextField notesInput;
    @FXML private Button createButton;


    public void createButtonClick() {
        String sql = "INSERT INTO otherEvents " +
                "(other_id, name, date, time, note)" +
                "VALUES (null, ?, ?, ?, ?)";
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, nameInputField.getText());

            // Passes date input to DB
            String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setString(2, date);

            // Passes time input to DB
            stmt.setString(3, timeInput.getText());

            // Passes notes input to DB
            stmt.setString(4, notesInput.getText());

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
