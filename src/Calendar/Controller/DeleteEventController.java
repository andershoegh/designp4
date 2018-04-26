package Calendar.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DeleteEventController {

    private boolean confirmDeletion;

    @FXML private Label eventNameLabel;
    @FXML private Button acceptButton;
    @FXML private Button closeButton;

    public void confirmButtonClick(){
        confirmDeletion = true;
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        stage.close();
    }

    public void closeButtonClick(){
        confirmDeletion = false;
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public boolean getConfirmValue(){
        return this.confirmDeletion;
    }


}
