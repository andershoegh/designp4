package Training.Controller;

import SQL.SqlConnection;
import javafx.fxml.FXML;
import Training.Program;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        try{
            Connection conn = SqlConnection.connectToDB();

            String sql = "DELETE FROM exercises WHERE program_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, selectedProgram.getId());
            statement.executeUpdate();

            SqlConnection.closeConnection();

        } catch (SQLException excp) {
            excp.printStackTrace();
        }

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
