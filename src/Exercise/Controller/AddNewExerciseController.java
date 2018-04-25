package Exercise.Controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TextField;
import Controller.MenuController;

public class AddNewExerciseController {

    @FXML private TextField trainingNameInput;
    @FXML private GridPane trainingExercises;
    @FXML private DatePicker trainingDate;
    @FXML private TextArea focusInput;
    @FXML private Listview attendingPlayers;
    @FXML private Listview declinedPlayers;

    @FXML private Button acceptButton;
    @FXML private Button printButton;
    @FMXL private Button addExercise;
    @FMXL private Button removeExercise;


    @FXML
    public void acceptButtonClick() {
        Connection conn = SqlConnection.connectToDB();
        String sql = "INSERT INTO exercises" + " (exercise_id, name, description";

        if(trainingExercises.getChildren().size()>0) {

        }
    }

    public void addExerciseButtonClick() {

    }


}
