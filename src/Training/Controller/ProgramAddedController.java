package Training.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//Controller for the feedback pop-up regarding a program is added.
public class ProgramAddedController {
    //FXML elements
    @FXML private Label programName;
    @FXML private Button acceptButton;

    //Sets the name of the program jjust added
    public void setText(String name) {
        programName.setText(name);
    }

    //Closes the pop-up
    public void acceptButtonClick() {
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        stage.close();
    }
}
