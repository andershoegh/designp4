package Match.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class saveLineupPopController {
    @FXML
    public Button okButton;

    public void okButtonClick(){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
