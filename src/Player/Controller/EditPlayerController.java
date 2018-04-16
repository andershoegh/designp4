package Player.Controller;

import Player.Player;
import SQL.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditPlayerController {

    private Player selectedPlayer;
    private int playerID;

    @FXML Button saveButton;

    // editable
    @FXML private Label playerName;
    @FXML private TextField position;
    @FXML private TextField address;
    @FXML private TextField telephone;
    @FXML private TextField mail;
    @FXML private TextField birthday;
    @FXML private CheckBox health;

    // stats display
    @FXML private Label motm;
    @FXML private Label goalsScored;
    @FXML private Label assists;
    @FXML private Label attendedMatches;
    @FXML private Label attendedTrainings;
    @FXML private Label yellowCards;
    @FXML private Label redCards;

    public void initData(Player player){
        selectedPlayer = player;
        playerID = selectedPlayer.getId();

        // inputting editable data into text fields
        playerName.setText(selectedPlayer.getName());
        position.setText(selectedPlayer.getPosition());
        address.setText(selectedPlayer.getAddress());
        telephone.setText(Integer.toString(selectedPlayer.getPhone()));
        mail.setText(selectedPlayer.getMail());

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

            String sql = "UPDATE player SET address = ?, phone = ?, mail = ?, position = ?, health = ? WHERE _id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, address.getText());
            stmt.setInt(2, Integer.parseInt(telephone.getText()));
            stmt.setString(3, mail.getText());
            stmt.setString(4, position.getText());
            stmt.setInt(5, health.isSelected() ? 1 : 0);
            stmt.setInt(6, playerID);

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
