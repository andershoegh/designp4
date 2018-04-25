package Calendar.Controller;

import SQL.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class CreateActivityMatchController {

    @FXML private TextField opponentInput;
    @FXML private DatePicker dateInput;
    @FXML private TextField timeInput;
    @FXML private CheckBox locationInput;
    @FXML private TextField notesInput;
    @FXML private Button createButton;

    public void createButtonClick() {
        String sql = "INSERT INTO matches " +
                "(match_id, opponent, goalsFor, goalsAgainst, season, date, time, isHome, note)" +
                "VALUES (null, ?, null , null, null,  ?, ?, ?, ?)";
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, opponentInput.getText() + " ");

            // Passes date input to DB
            String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setString(2, date);

            // Passes time input to DB
            stmt.setString(3, timeInput.getText());

            // Passes location input to DB
            stmt.setInt(4, locationInput.isSelected() ? 1 : 0);

            //Passes notes input to DB
            stmt.setString(5, notesInput.getText());

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

