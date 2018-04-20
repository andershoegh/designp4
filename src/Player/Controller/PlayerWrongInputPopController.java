package Player.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

    public class PlayerWrongInputPopController {

    @FXML private Button acceptButton;

    public void acceptButton(){
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        stage.close();
    }
}
