package Player.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import SQL.SqlConnection;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import static java.lang.String.valueOf;

public class addNewPlayerController {

    // This is a connection between the FXML file and the DB, inserting values.
    @FXML private TextField nameInput;
    @FXML private TextField addressInput;
    @FXML private TextField phoneInput;
    @FXML private TextField mailInput;
    @FXML private TextField ICEnameInput;
    @FXML private TextField ICEphoneInput;
    @FXML private DatePicker birthdayInput;
    @FXML private ChoiceBox positionInput;
    @FXML private CheckBox health;
    @FXML private Button acceptButton;
    @FXML private Button cancelButton;
    @FXML private Label label_health;

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


        // Forces input in phone field to only accept numerical values
        phoneInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void handleLabel_health(){
            System.out.println("--- Health label is clicked, and therefore the 'Health' is activated/deactivated. ---");
            health.fire();
    }

    public void acceptButtonClick() throws IOException {
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
            if(birthdayInput.getValue() == null){
                stmt.setString(9, null);
            } else {
                String date = birthdayInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                stmt.setString(9, date);
            }

            // Updates the database
            stmt.executeUpdate();

            // Closes the connected database
            SqlConnection.closeConnection();

            // Opens new window, so the player can see feedback, and closes the "Add Player" window, after the user clicks "Ok."
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../PlayerAdded-Pop-up.fxml"));
            Parent playerAddedPopFXML = loader.load();
            PlayerAddedPopController cont = loader.getController();

            cont.setText(nameInput.getText());
            Scene playerAddedScene = new Scene(playerAddedPopFXML);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Spiller tilføjet");
            stage.setScene(playerAddedScene);
            stage.showAndWait();
            // Closing the window and returning to addPlayerFXML.fxm
            stage.close();
            // Closing the window and returning to PlayerList.fxml
            cancelButtonClick();

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

