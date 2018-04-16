package Player.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import SQL.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addNewPlayerController {

    // This is a connection between the FXML file and the DB, inserting values.
    @FXML private TextField nameInput;
    @FXML private TextField addressInput;
    @FXML private TextField phoneInput;
    @FXML private TextField mailInput;
    @FXML private TextField ICEnameInput;
    @FXML private TextField ICEphoneInput;
    @FXML private TextField positionInput;
    @FXML private TextField birthdayInput;
    @FXML private CheckBox health;
    @FXML private Button acceptButton;
    @FXML private Button cancelButton;

    public void acceptButtonClick(){

        Connection conn = SqlConnection.connectToDB();
        String sql = "INSERT INTO player "
                + " (_id, name, address, phone, mail," +
                "iceName, iceTelephone, position, health, " +
                "yellowCards, redCards, goalScored, assist, motm, " +
                "attendedMatches, attendedTrainings)" +

                " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, null, null, null, null, null, null, null)";

        // Connecting to the database
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Inserting the values into the database
            stmt.setString(1, nameInput.getText());
            stmt.setString(2, addressInput.getText());
            stmt.setInt(3, Integer.parseInt(phoneInput.getText())); // String being parsed to int, to give it to DB.
            stmt.setString(4, mailInput.getText());
            stmt.setString(5, ICEnameInput.getText());
            stmt.setInt(6, Integer.parseInt(ICEphoneInput.getText())); // String being parsed to int, to give it to DB.
            stmt.setString(7, positionInput.getText());
            stmt.setInt(8, health.isSelected() ? 1 : 0);

            // Updates the database
            stmt.executeUpdate();

            SqlConnection.closeConnection();

            // Closing the window and returning to PlayerList.fxml
            Stage stage = (Stage) acceptButton.getScene().getWindow();
            stage.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelButtonClick(){
        // Closing the window and returning to PlayerList.fxml
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
