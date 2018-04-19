package Player.Controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import SQL.SqlConnection;
import javafx.fxml.FXML;
/*
import org.omg.CORBA.INTERNAL;
*/
import java.sql.*;
<<<<<<< HEAD
import java.time.format.DateTimeFormatter;
import static java.lang.String.valueOf;
=======
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.lang.String.valueOf;
import static java.time.LocalDate.*;


public class addNewPlayerController {

    // This is a connection between the FXML file and the DB, inserting values.
    @FXML private TextField nameInput;
    @FXML private TextField addressInput;
    @FXML private TextField phoneInput;
    @FXML private TextField mailInput;
    @FXML private TextField ICEnameInput;
    @FXML private TextField ICEphoneInput;
    @FXML private ChoiceBox positionInput;
    @FXML private DatePicker birthdayInput;
    @FXML private CheckBox health;
    @FXML private Button acceptButton;
    @FXML private Button cancelButton;

    @FXML
    public void initialize() {
        positionInput.getItems().removeAll(positionInput.getItems());
        positionInput.getItems().addAll(
                "Angriber",
                "Central Forsvarsspiller",
                "Back",
                "Midtbanespiller",
                "Midtbanespiller (Offensiv)",
                "Midtbanespiller (Defensiv)",
                "Midtbanespiller (Central)",
                "Midtbanespiller (Kant)",
                "Målmand",
                "Andet");
        positionInput.getSelectionModel().select("Angriber");
    }

    public void acceptButtonClick(){
        Connection conn = SqlConnection.connectToDB();
        String sql = "INSERT INTO players "
                + " (player_id, name, address, phone, mail," +
                "iceName, iceTelephone, position, health, birthday, " +
                "yellowCards, redCards, goalScored, assist, motm, " +
                "attendedMatches, attendedTrainings)" +

                " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, null, null, null, null, null, null, null)";


        // Connecting to the database
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Inserts data into the "name" field in the database. If there is no data, it will set the string to "null"
            if (nameInput.getText().equals("")){
                stmt.setString(1, null);
            } else {
                stmt.setString(1, nameInput.getText());
            }
>>>>>>> master

            // Inserts data into the "address" field in the database. If there is no data, it will set the string to "null"
            if (addressInput.getText().equals("")){
                stmt.setString(2, null);
            } else {
                stmt.setString(2, addressInput.getText());
            }

            // Inserts data into the "phone" field in the database. If there is no data, it will set the string to "null"
            if (phoneInput.getText().equals("")){
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, Integer.parseInt(phoneInput.getText())); // String being parsed to int, to give it to DB.
            }

            // Inserts data into the "mail" field in the database. If there is no data, it will set the string to "null"
            if (mailInput.getText().equals("")){
                stmt.setString(4, null);
            } else {
                stmt.setString(4, mailInput.getText());
            }

            // Inserts data into the "iceName" field in the database. If there is no data, it will set the string to "null"
            if (ICEnameInput.getText().equals("")){
                stmt.setString(5, null);
            } else {
                stmt.setString(5, ICEnameInput.getText());
            }

            // Inserts data into the "iceTelephone" field in the database. If there is no data, it will set the string to "null"
            if (ICEphoneInput.getText().equals("")){
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, Integer.parseInt(ICEphoneInput.getText())); // String being parsed to int, to give it to DB.
            }

            // Inserts data into the "position" field in the database
            stmt.setString(7, valueOf(positionInput.getSelectionModel().getSelectedItem()));

            // Inserts data into the "health" field in the database
            stmt.setInt(8, health.isSelected() ? 1 : 0);

            // Creates a string, from the birthdayInput, and
            // inserts data into the "birthday" field in the database.
            // If there is no data, it will set the string to "null"
            if(birthdayInput.getValue().equals("")){
                stmt.setString(9, null);
            } else {
                String date = birthdayInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                stmt.setString(9, date);
            }

            // Updates the database
            stmt.executeUpdate();

            // Closes the connected database
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