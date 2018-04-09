package Player.Controller;

import SQL.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Player.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeletePlayerController {

    private Player selectedPlayer;
    private int playerId;
    @FXML private Label playerNameLabel;
    @FXML private Button acceptButton;
    @FXML private Button closeButton;


    // Extracts the name from the selected player
    public void initData(Player player){
        selectedPlayer = player;
        playerNameLabel.setText(selectedPlayer.getName());
        playerId = selectedPlayer.getId();
    }

    public void confirmButtonClick(){
        try {
            Connection conn = SqlConnection.connectToDB();

            String sql = "DELETE FROM player WHERE _id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, playerId);
            statement.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) acceptButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeButtonClick(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
