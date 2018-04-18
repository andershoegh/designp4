package Player.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import SQL.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
/*
import org.omg.CORBA.INTERNAL;
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

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
        String sql = "INSERT INTO players "
                + " (player_id, name, address, phone, mail," +
                "iceName, iceTelephone, position, health, " +
                "yellowCards, redCards, goalScored, assist, motm, " +
                "attendedMatches, attendedTrainings)" +

                " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, null, null, null, null, null, null, null)";


        // Connecting to the database
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            if (nameInput.getText().equals("")){
                stmt.setString(1, null);
            } else {
                stmt.setString(1, nameInput.getText());
            }

            if (addressInput.getText().equals("")){
                stmt.setString(2, null);
            } else {
                stmt.setString(2, addressInput.getText());
            }

            if (phoneInput.getText().equals("")){
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, Integer.parseInt(phoneInput.getText())); // String being parsed to int, to give it to DB.
            }

            if (mailInput.getText().equals("")){
                stmt.setString(4, null);
            } else {
                stmt.setString(4, mailInput.getText());
            }

            if (ICEnameInput.getText().equals("")){
                stmt.setString(5, null);
            } else {
                stmt.setString(5, ICEnameInput.getText());
            }

            if (ICEphoneInput.getText().equals("")){
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, Integer.parseInt(ICEphoneInput.getText())); // String being parsed to int, to give it to DB.
            }

            if (positionInput.getText().equals("")){
                stmt.setString(7, null);
            } else {
                stmt.setString(7, positionInput.getText());
            }


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
