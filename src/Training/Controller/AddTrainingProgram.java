package Training.Controller;

import SQL.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTrainingProgram {
    //FXML elements
    @FXML private TextField programNameText;
    @FXML private Button addProgramButton;
    @FXML private Button cancelButton;

    //Adding a new training program, when button is clicked
    public void acceptButtonClick() {
        //Connects to DB and inserts into programs-table
        String sql = "INSERT INTO programs (program_id, name) VALUES (null, ?)";
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Only inserts name of program and autogenerates id
            stmt.setString(1, programNameText.getText());

            stmt.executeUpdate();

            SqlConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Closing the pop-up
        Stage stage = (Stage) addProgramButton.getScene().getWindow();
        stage.close();
    }

    //Function for closing the pop-up
    public void cancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
