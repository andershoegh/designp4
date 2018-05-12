package Match.Controller;

import Match.Match;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class DeleteMatchController {
    private Match selectedMatch;

    @FXML private Label matchNameLabel;
    @FXML private Label matchDateLabel;
    @FXML private Button acceptButton;
    @FXML private Button closeButton;

    // Extracts the name from the selected player
    public void initData(Match match){
        selectedMatch = match;
        matchNameLabel.setText(selectedMatch.getOpponent());
        matchDateLabel.setText("D. " + selectedMatch.getDate());
    }

    public void confirmButtonClick(){
            selectedMatch.delete();
            Stage stage = (Stage) acceptButton.getScene().getWindow();
            stage.close();
    }

    public void closeButtonClick(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
