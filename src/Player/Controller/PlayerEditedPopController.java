package Player.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PlayerEditedPopController {

    @FXML private Label playerName_EDIT;
    @FXML private Button acceptButton_EDIT;

    public void setText(String name){
        playerName_EDIT.setText(name);
    }

    public void acceptButton(){
        Stage stage = (Stage) acceptButton_EDIT.getScene().getWindow();
        stage.close();

    }
}

