package Player.Controller;

import Player.Player;
import SQL.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
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


    @FXML Button saveButton;

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
    }

    public void initData(Player player) throws ParseException {
        selectedPlayer = player;
        playerID = selectedPlayer.getId();

        // inputting editable data into text fields
        playerName.setText(selectedPlayer.getName());
        nameInput.setText(selectedPlayer.getName());
        address.setText(selectedPlayer.getAddress());
        telephone.setText(Integer.toString(selectedPlayer.getPhone()));
        mail.setText(selectedPlayer.getMail());
        ICEnameInput.setText(selectedPlayer.getICEname());
        ICEphoneInput.setText(Integer.toString(selectedPlayer.getICEtelephone()));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        if (selectedPlayer.getBirthday() == null){
            birthday.setValue(localDate);
        } else {
            Date d = format.parse(selectedPlayer.getBirthday());
            LocalDate localDate = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            birthday.setValue(localDate);
        }
        position.setValue(selectedPlayer.getPosition());

        // setting checkbox to fire if status == 1
        if(selectedPlayer.getHealth() == 1){
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

    public void saveButtonClick(){
        try {
            Connection conn = SqlConnection.connectToDB();

            String sql = "UPDATE players SET name = ?, " +
                    "address = ?, phone = ?, mail = ?, " +
                    "iceName = ?, iceTelephone = ?, " +
                    "birthday = ?, position = ?," +
                    "health = ? WHERE player_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            // Inserts data into the "name" field in the database. If there is no data, it will set the string to "null"
            if (nameInput.getText().equals("")){
                stmt.setString(1, null);
            } else {
                stmt.setString(1, nameInput.getText());
            }

            // Inserts data into the "address" field in the database. If there is no data, it will set the string to "null"
            if (address.getText().equals("")){
                stmt.setString(2, null);
            } else {
                stmt.setString(2, address.getText());
            }

            // Inserts data into the "phone" field in the database. If there is no data, it will set the string to "null"
            if (telephone.getText().equals("")){
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, Integer.parseInt(telephone.getText())); // String being parsed to int, to give it to DB.
            }

            // Inserts data into the "mail" field in the database. If there is no data, it will set the string to "null"
            if (mail.getText().equals("")){
                stmt.setString(4, null);
            } else {
                stmt.setString(4, mail.getText());
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
            stmt.setString(7, valueOf(position.getSelectionModel().getSelectedItem()));

            // Inserts data into the "health" field in the database
            stmt.setInt(8, health.isSelected() ? 1 : 0);

            // Creates a string, from the birthdayInput, and
            // inserts data into the "birthday" field in the database.
            // If there is no data, it will set the string to "null"
            if(birthday.getValue() == null){
                stmt.setString(9, null);
            } else {
                String date = birthday.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                stmt.setString(9, date);
            }
            stmt.setInt(10, playerID);

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
