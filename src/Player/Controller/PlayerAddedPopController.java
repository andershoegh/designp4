package Player.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PlayerAddedPopController {

    @FXML private Label playerName;
    @FXML private Button acceptButton;

    public void setText(String name){
        playerName.setText(name);
    }

    public void acceptButton(){
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        stage.close();

    }
}

