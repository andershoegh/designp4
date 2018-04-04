package player.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import player.Player;

public class DeletePlayerController {

    private Player selectedPlayer;
    @FXML private Label playerNameLabel;
    @FXML private Button closeButton;


    // Extracts the name from the selected player
    public void initData(Player player){
        selectedPlayer = player;
        playerNameLabel.setText(selectedPlayer.getName());
    }

    public void closeButtonClick(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
