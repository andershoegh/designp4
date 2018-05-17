package Training.Controller;

import javafx.fxml.FXML;
import Training.Program;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DeleteTrainingProgramController {

    //Local field for containing the program to delete
    private Program selectedProgram;

    //FXML elements
    @FXML private Label programName;
    @FXML private Button acceptButton;
    @FXML private Button cancelButton;

    //Retrieve the program to delete through parameter
    public void initData(Program program) {
        selectedProgram = program;
        programName.setText(selectedProgram.getName());
    }

    //Calling delete method in program-class if button is clicked
    public void acceptButtonClick(){
        selectedProgram.delete();
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        stage.close();
    }

    //Closes the pop-up
    public void cancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
