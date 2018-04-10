package Player.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import SQL.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
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
    @FXML private TextField healthInput;

    public void acceptButtonClick(ActionEvent event){

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
            stmt.setInt(3, Integer.parseInt(phoneInput.getText()));
            stmt.setString(4, mailInput.getText());
            stmt.setString(5, ICEnameInput.getText());
            stmt.setInt(6, Integer.parseInt(ICEphoneInput.getText())); // String being parsed to int, to give it to DB.
            stmt.setString(7, positionInput.getText());
            stmt.setString(8, healthInput.getText());

            // Updates the database
            stmt.executeUpdate();

            // Switching scene from AddPlayer.FXML to PlayerList.FXML
            Parent addPlayerFXML = FXMLLoader.load(getClass().getResource("../PlayerList.fxml"));
            Scene addPlayerScene = new Scene(addPlayerFXML);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(addPlayerScene);
            stage.show();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            e.printStackTrace();
        }

        SqlConnection.closeConnection();
    }

    public void cancelButtonClick(ActionEvent event){
        // Switching scene from AddPlayer.FXML to PlayerList.FXML
        try {
            Parent addPlayerFXML = FXMLLoader.load(getClass().getResource("../PlayerList.fxml"));
            Scene addPlayerScene = new Scene(addPlayerFXML);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(addPlayerScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
