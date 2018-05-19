package Training.Controller;

import Training.Exercise;
import Training.Program;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import SQL.SqlConnection;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.*;
import java.lang.String;
import java.util.ArrayList;

public class EditTrainingProgramController {
    //Field for containing the program
    private Program program;
    //Field for containing the list of exercises for the program
    private ArrayList<Exercise> exercises;

    //FXML elements for editing program information
    @FXML private TextField programNameText;
    @FXML private TextField programDuration;
    @FXML private TextArea notesText;

    //FXML text fields for the 11 exercises that can be creates in a training program
    //Textfield for name of exercise and duration
    @FXML private TextField exerciseDuration0;
    @FXML private TextField exerciseName0;
    @FXML private TextField exerciseDuration1;
    @FXML private TextField exerciseName1;
    @FXML private TextField exerciseDuration2;
    @FXML private TextField exerciseName2;
    @FXML private TextField exerciseDuration3;
    @FXML private TextField exerciseName3;
    @FXML private TextField exerciseDuration4;
    @FXML private TextField exerciseName4;
    @FXML private TextField exerciseDuration5;
    @FXML private TextField exerciseName5;
    @FXML private TextField exerciseDuration6;
    @FXML private TextField exerciseName6;
    @FXML private TextField exerciseDuration7;
    @FXML private TextField exerciseName7;
    @FXML private TextField exerciseDuration8;
    @FXML private TextField exerciseName8;
    @FXML private TextField exerciseDuration9;
    @FXML private TextField exerciseName9;
    @FXML private TextField exerciseDuration10;
    @FXML private TextField exerciseName10;
    @FXML private Button cancelButton;

    //Initial data gets loaded via the program as parameter
    public void initData(Program program) {
        this.program = program;
        exercises = new ArrayList<>();
        loadDataFromDB();
        insertExercises();

        programNameText.setText(program.getName());
        programDuration.setText(program.getDuration());
        notesText.setText(program.getNotes());
    }

    //Loads exercises from database, which contains a foreign key mathcing the program ID
    private void loadDataFromDB() {
        Connection conn = SqlConnection.connectToDB();
        try {
            PreparedStatement programStatement = conn.prepareStatement("SELECT * FROM exercises WHERE program_id=?");
            programStatement.setInt(1, program.getId());
            ResultSet rsExercise = programStatement.executeQuery();

            //Adding each mathcing exercise to the 'exercises' arraylist
            while (rsExercise.next()) {
                exercises.add(new Exercise(rsExercise.getString("name"),
                        rsExercise.getString("duration"),
                        rsExercise.getInt("exercise_id")));
            }

            SqlConnection.closeConnection();

        } catch (SQLException excp) {
            excp.printStackTrace();
        }
    }

    //Inserts each exercise loaded from the db, into a mathcing pair of textfields
    public void insertExercises(){
        //Checks if exercises-arraylist is empty
        if(!(exercises.isEmpty())){
            //Looping through the arraylist
            for (int i = 0; i<exercises.size(); i++){
                //Inserts the exercise in the corresponding pair of textfield
                if(i == 0){
                    exerciseName0.setText(exercises.get(i).getName());
                    exerciseDuration0.setText(exercises.get(i).getDuration());
                }
                else if(i == 1){
                    exerciseName1.setText(exercises.get(i).getName());
                    exerciseDuration1.setText(exercises.get(i).getDuration());
                }
                else if(i == 2){
                    exerciseName2.setText(exercises.get(i).getName());
                    exerciseDuration2.setText(exercises.get(i).getDuration());
                }
                else if(i == 3){
                    exerciseName3.setText(exercises.get(i).getName());
                    exerciseDuration3.setText(exercises.get(i).getDuration());
                }
                else if(i == 4){
                    exerciseName4.setText(exercises.get(i).getName());
                    exerciseDuration5.setText(exercises.get(i).getDuration());
                }
                else if(i == 5){
                    exerciseName5.setText(exercises.get(i).getName());
                    exerciseDuration6.setText(exercises.get(i).getDuration());
                }
                else if(i == 6){
                    exerciseName6.setText(exercises.get(i).getName());
                    exerciseDuration6.setText(exercises.get(i).getDuration());
                }
                else if(i == 7){
                    exerciseName7.setText(exercises.get(i).getName());
                    exerciseDuration7.setText(exercises.get(i).getDuration());
                }
                else if(i == 8){
                    exerciseName8.setText(exercises.get(i).getName());
                    exerciseDuration8.setText(exercises.get(i).getDuration());
                }
                else if(i == 9){
                    exerciseName9.setText(exercises.get(i).getName());
                    exerciseDuration9.setText(exercises.get(i).getDuration());
                }
                else if(i == 10){
                    exerciseName10.setText(exercises.get(i).getName());
                    exerciseDuration10.setText(exercises.get(i).getDuration());
                }
            }
        }
    }

    //Function for saving the data into the database
    public void safeButtonClick() throws IOException {
        //First removes exercise data regarding this program in the db
        removeFromDB();
        //Updates the exercises arraylist to match the data in the textfields in the FXML page
        updateExerciseList();

        //The training program and the exercises are two different tables in the DB the connection is like this
        //Overriding the training program
        String sql = "UPDATE programs SET name = ?, notes = ?, duration = ?, numExercises = ?"
                + " WHERE program_id = ?";

        //Inserts new elements into exercises-table
        String sql2 = "INSERT INTO exercises"
                + "(exercise_id, name, duration, program_id)"
                + " VALUES (NULL, ?, ?, ?)";

        //connection to database
        try {
            Connection conn = SqlConnection.connectToDB();

            PreparedStatement stmt = conn.prepareStatement(sql); //connection to programs in DB

            //Retrieves the data from the FXML text fields and updates the training program
            stmt.setString(1,programNameText.getText());
            stmt.setString(2,notesText.getText());
            stmt.setString(3, programDuration.getText());
            stmt.setInt(4, exercises.size());
            stmt.setInt(5, program.getId());
            stmt.executeUpdate();

            //Loops through the exercises arraylist and inserts each exercise into the exercises-table
            //with a reference to the program ID
            for (Exercise exercise: exercises) {
                PreparedStatement stmt2 = conn.prepareStatement(sql2); //connection to programs in DB
                stmt2.setString(1,exercise.getName());
                stmt2.setString(2,exercise.getDuration());
                stmt2.setInt(3, program.getId());
                stmt2.executeUpdate();
            }

            SqlConnection.closeConnection();

            //if the input is the correct format the user will get a pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ProgramSaved.fxml"));
            Parent programAddedFXML = loader.load();
            ProgramAddedController cont = loader.getController();

            //Passing name of program to controller
            cont.setText(program.getName());
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
        }
    }

    //Function for updating the 'exercises' arraylist to match whatever is in the FXML text fields
    private void updateExerciseList() {
        //Clearing the current data in the arraylist
        exercises.clear();

        //Checking if a textfield contains text
        if(!(exerciseName0.getText().isEmpty())){
            //Adds the data to the 'exercises' arraylist via constructing a exercise based on the data from the text fields
            exercises.add( new Exercise(exerciseName0.getText(), exerciseDuration0.getText()));
        }
        if(!(exerciseName1.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName1.getText(), exerciseDuration1.getText()));
        }
        if(!(exerciseName2.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName2.getText(), exerciseDuration2.getText()));
        }
        if(!(exerciseName3.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName3.getText(), exerciseDuration3.getText()));
        }
        if(!(exerciseName4.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName4.getText(), exerciseDuration4.getText()));
        }
        if(!(exerciseName5.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName5.getText(), exerciseDuration5.getText()));
        }
        if(!(exerciseName6.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName6.getText(), exerciseDuration6.getText()));
        }
        if(!(exerciseName7.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName7.getText(), exerciseDuration7.getText()));
        }
        if(!(exerciseName8.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName8.getText(), exerciseDuration8.getText()));
        }
        if(!(exerciseName8.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName8.getText(), exerciseDuration8.getText()));
        }
        if(!(exerciseName9.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName9.getText(), exerciseDuration9.getText()));
        }
        if(!(exerciseName10.getText().isEmpty())){
            exercises.add( new Exercise(exerciseName10.getText(), exerciseDuration10.getText()));
        }
    }

    //Removes all exercises in DB with a reference to the program ID
    //Doing this to load new exercises, so no one is repeated
    private void removeFromDB() {
        Connection conn = SqlConnection.connectToDB();
        String sql = "DELETE FROM exercises WHERE program_id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, program.getId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            SqlConnection.closeConnection();
        }
    }

    //Function for closing the pop-up
    public void cancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
