package Player.Controller;

import Player.Player;
import SQL.SqlConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static java.lang.String.valueOf;

public class EditPlayerController {

    private Player selectedPlayer;
    private int playerID;
    private LocalDate localDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");


    @FXML private Button saveButton;
    @FXML private Button cancelButton_EDIT;

    // editable
    @FXML private Label playerName;
    @FXML private TextField nameInput;
    @FXML private TextField address;
    @FXML private TextField telephone;
    @FXML private TextField mail;
    @FXML private TextField ICEnameInput;
    @FXML private TextField ICEphoneInput;
    @FXML private DatePicker birthday;
    @FXML private ChoiceBox position;
    @FXML private CheckBox health;
    @FXML private Label health_label;

    // stats display
    @FXML private Label motm;
    @FXML private Label goalsScored;
    @FXML private Label assists;
    @FXML private Label attendedMatches;
    @FXML private Label attendedTrainings;
    @FXML private Label yellowCards;
    @FXML private Label redCards;

    @FXML
    public void initialize() throws ParseException {
        position.getItems().removeAll(position.getItems());
        position.getItems().addAll(
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
        position.getSelectionModel().select("Angriber");

        // Forces input in phone field to only accept numerical values
        telephone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    telephone.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    // Inputting editable data into text fields
    public void initData(Player player) throws ParseException {
        selectedPlayer = player;
        playerID = selectedPlayer.getId();
        // TITLE NAME
        playerName.setText(selectedPlayer.getName());
        // NAME
        nameInput.setText(selectedPlayer.getName());
        // ADDRESS
        address.setText(selectedPlayer.getAddress());
        // TELEPHONE
        // If the value from the database is set to "null" the phone-field will display
        // "0" in the textfield. This if-statement says, that if the value is "0", then
        // display nothing. If the value is not null, then display the phonenumber.
        if (selectedPlayer.getPhone() == 0) {
            telephone.setText("");
        } else {
            telephone.setText(Integer.toString(selectedPlayer.getPhone()));
        }
        // MAIL
        mail.setText(selectedPlayer.getMail());
        // IN-CASE-OF-EMERGENCY NAME
        ICEnameInput.setText(selectedPlayer.getICEname());
        // IN-CASE-OF-EMERGENCY PHONE
        // If the value from the database is set to "null" the phone-field will display
        // "0" in the textfield. This if-statement says, that if the value is "0", then
        // display nothing. If the value is not null, then display the phonenumber.
        if (selectedPlayer.getICEtelephone() == 0) {
            ICEphoneInput.setText("");
        } else {
            ICEphoneInput.setText(Integer.toString(selectedPlayer.getICEtelephone()));
        }
        // BIRTHDAY
        // This function formats the data from the "birthday" field in the database
        // and displays it the correct way. If there is no data, the field will be
        // empty. But if there is a value, it will be displayed as "dd/mm/yyyy"
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        if (selectedPlayer.getBirthday() == null) {
            birthday.setValue(localDate);
        } else {
            Date d = format.parse(selectedPlayer.getBirthday());
            LocalDate localDate = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            birthday.setValue(localDate);
        }
        // POSITION
        position.setValue(selectedPlayer.getPosition());
        // HEALTH
        // Setting checkbox to fire if status == 1
        if (selectedPlayer.getHealth() == 1) {
            health.fire();
        }

        // inputting displayed stats to labels
        motm.setText(Integer.toString(selectedPlayer.getMotm()));
        goalsScored.setText(Integer.toString(selectedPlayer.getGoalsScored()));
        assists.setText(Integer.toString(selectedPlayer.getAssists()));
        attendedMatches.setText(Integer.toString(selectedPlayer.getAttendedMatches()));
        attendedTrainings.setText(Integer.toString(selectedPlayer.getAttendedTrainings()));
        yellowCards.setText(Integer.toString(selectedPlayer.getYellowCards()));
        redCards.setText(Integer.toString(selectedPlayer.getRedCards()));
    }

    public void health_label_clicked() {
        System.out.println("--- Health label is clicked, and therefore the 'Health' is activated/deactivated. ---");
        health.fire();
    }

    public void saveButtonClick() throws IOException {
        try {
            Connection conn = SqlConnection.connectToDB();

            String sql = "UPDATE players SET name = ?, " +
                    "address = ?, phone = ?, mail = ?, " +
                    "iceName = ?, iceTelephone = ?, " +
                    "birthday = ?, position = ?," +
                    "health = ? WHERE player_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            // NAME
            stmt.setString(1, nameInput.getText());
            // ADDRESS
            stmt.setString(2, address.getText());
            // PHONE
            // Inserts data into the "phone" field in the database. If there is no data, it will set the string to "null"
            if (telephone.getText().equals("")) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, Integer.parseInt(telephone.getText())); // String being parsed to int, to give it to DB.
            }
            // MAIL
            stmt.setString(4, mail.getText());
            // IN-CASE-OF-EMERGENCY NAME
            stmt.setString(5, ICEnameInput.getText());
            // IN-CASE-OF-EMERGENCY PHONE
            // Inserts data into the "iceTelephone" field in the database. If there is no data, it will set the string to "null"
            if (ICEphoneInput.getText().equals("")) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, Integer.parseInt(ICEphoneInput.getText())); // String being parsed to int, to give it to DB.
            }
            // BIRTHDAY
            // Creates a string, from the birthdayInput, and
            // inserts data into the "birthday" field in the database.
            // If there is no data, it will set the string to "null"
            if (birthday.getValue() == null) {
                stmt.setString(7, null);
            } else {
                String date = birthday.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                stmt.setString(7, date);
            }
            // POSITION
            if (position.getValue() == null) {
                stmt.setString(8, null);
            } else {
                stmt.setString(8, valueOf(position.getSelectionModel().getSelectedItem()));
            }
            // HEALTH
            stmt.setInt(9, health.isSelected() ? 1 : 0);
            // PLAYER ID
            stmt.setInt(10, playerID);

            // Updates the database
            stmt.executeUpdate();
            // Closes the connection to the database
            SqlConnection.closeConnection();


            // Opens new window, so the player can see feedback, and closes the "Edit window", after the user clicks "Ok."
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../EditPlayer-Edited-Pop-up.fxml"));
            Parent editPlayerFXML = loader.load();
            PlayerEditedPopController cont = loader.getController();

            cont.setText(nameInput.getText());
            Scene playerEditedScene = new Scene(editPlayerFXML);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Spiller ændret");
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            stage.setScene(playerEditedScene);
            stage.showAndWait();
            // Closing the window and returning to addPlayerFXML.fxml
            stage.close();
            // Closing the window and returning to PlayerList.fxml
            cancelButtonClick_EDIT();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelButtonClick_EDIT() {
        // Closing the window and returning to PlayerList.fxml
        Stage stage = (Stage) cancelButton_EDIT.getScene().getWindow();
        stage.close();
    }
}
