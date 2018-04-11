package Player.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditPlayerController {

    // editable
    @FXML private Label playerName;
    @FXML private TextField placement;
    @FXML private TextField address;
    @FXML private TextField telephone;
    @FXML private TextField mail;
    @FXML private TextField birthday;
    @FXML private CheckBox status;

    // stats display
    @FXML private Label motm;
    @FXML private Label goalsScored;
    @FXML private Label assists;
    @FXML private Label attendedMatches;
    @FXML private Label attendedTrainings;
    @FXML private Label yellowCards;
    @FXML private Label redCards;
}
