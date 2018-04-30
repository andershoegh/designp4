package Training.Controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TextField;
import Controller.MenuController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import SQL.SqlConnection;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import static java.lang.String.valueOf;

public class AddNewTrainingProgramController {

    @FXML private TextField programNameInput;
    @FXML private GridPane trainingExercises;
    @FXML private TextField exerciseDuration;
    @FXML private TextField exerciseName;
    @FXML private TextArea focusInput;
    @FXML private TextField programDuration;
    @FXML private Button addExercise;
    @FXML private Button acceptButton;
    @FXML private Button cancelButton;
    @FXML private Button printButton;


    // if I have time??
    @FXML private Button removeExercise;


    public void initialize() {


    }

    public void addExerciseButtonClick() {
        //adds a new row of TextFields to the GridPane including the exercises
        GridPane gridPane = new GridPane();

        TextField textField = new TextField();
        gridPane.add(textField, 0,1);

        TextField textField1 = new TextField();
        gridPane.add(textField, 1, 1);

    }

    public void acceptButtonClick() throws IOException {
        Connection conn = SqlConnection.connectToDB();
        //The training program and the exercises are two different tables in the DB the connection is like this
        String sql = "INSERT INTO programs"
                + "(program_id, name, description, duration)"
                + "VALUES (NULL, ?, ?, ?)";

        String sql2 = "INSERT INTO exercises"
                + "(exercise_id, duration, name)"
                + "VALUES (NULL, ?, ?)";

        //connection to database
        try {
            PreparedStatement stmt = conn.prepareStatement(sql); //connection to programs in DB

            //inserts data into given field, if there is no data then NULL

            if(programNameInput.getText().equals("")) {
                stmt.setString(1,null);
            } else {
                stmt.setString(1,programNameInput.getText());
            }

            if(focusInput.getText().equals("")) {
                stmt.setString(2,null);
            } else {
                stmt.setString(2, focusInput.getText());
            }

            if(programDuration.getText().equals("")) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, Integer.parseInt(programDuration.getText())); //Parsing string into int
            }

            PreparedStatement stmt2 = conn.prepareStatement(sql2); //connection to exercises in DB

            if(exerciseDuration.getText().equals("")) {
                stmt.setNull(1,Types.INTEGER);
            } else {
                stmt.setInt(1, Integer.parseInt(exerciseDuration.getText())); //Parsing string into int
            }

            if(exerciseName.getText().equals("")) {
                stmt.setString(2, null);
            } else {
                stmt.setString(2, exerciseName.getText());
            }

            stmt.executeUpdate();
            stmt2.executeUpdate();

            SqlConnection.closeConnection();

            //if the input is the correct format the user will get this pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ProgramAdded.fxml"));
            Parent programAddedFXML = loader.load();
            ProgramAddedController cont = loader.getController();

            cont.setText(programNameInput.getText());
            Scene programAddedScene = new Scene(programAddedFXML);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Træningsprogram tilføjet");
            stage.setScene(programAddedScene);
            stage.showAndWait();
            stage.close();

            cancelButtonClick();

        } catch (SQLException excp) {
            System.out.println(excp.getMessage());

            // If the input is the wrong format the user gets this error pop-up
        } catch (NumberFormatException excp) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ProgramWrongInput.fxml"));
            Parent wrongInputFXML = loader.load();
            ProgramWrongInputController cont = loader.getController();

            Scene wrongInputScene = new Scene(wrongInputFXML);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Forkert input");
            stage.setScene(wrongInputScene);
            stage.showAndWait();
            stage.close();
        }

    }

    public void cancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
