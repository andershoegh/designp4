package Training.Controller;

import Controller.MenuController;
import Training.Program;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainingOverviewController {
    private ObservableList<Program> programData = FXCollections.observableArrayList();

    //Connecting FXML elements
    @FXML private TableView<Program> tablePrograms;
    @FXML private TableColumn<?, ?> columnName;
    @FXML private TableColumn<?, ?> columnDuration;

    //Calling function, when page is initialized
    @FXML public void initialize(){
        programData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
    }

    //Funktion for clearing the tableview
    public void clearTable() {
        tablePrograms.getItems().clear();
    }

    //Defines where the columns shall retrieve data
    private void setCellTable() {
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    //Loads training programs from database
    private void loadDataFromDB() {
        Connection conn = SqlConnection.connectToDB();
        try {
            PreparedStatement programStatement = conn.prepareStatement("SELECT * FROM programs");
            ResultSet rsProgram = programStatement.executeQuery();

            //Adding the training programs to an arraylist
            while (rsProgram.next()) {
                programData.add(new Program(rsProgram.getInt("program_id"),
                        rsProgram.getString("name"),
                        rsProgram.getString("notes"),
                        rsProgram.getString("duration"))
                );
            }

            SqlConnection.closeConnection();

        } catch (SQLException excp) {
            excp.printStackTrace();
        }

        //Sets items from arraylist into the tableview
        tablePrograms.setItems(programData);
    }

    //Function, which creates a new training program, when button is clicked
    public void addProgram(ActionEvent event) {
        //switches from Overview to addTrainingProgram
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../AddTrainingProgram.fxml"));
            Parent addProgramFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene addProgramScene = new Scene(addProgramFXML);
            stage.setScene(addProgramScene);
            stage.showAndWait();

            //Clearing table view and initialize to retrieve an updated DB version
            clearTable();
            initialize();

        } catch (IOException excp) {
            excp.printStackTrace();
        }
    }

    //Function for editing a training program
    public void editProgram(ActionEvent event) {
        //switches from Overview to EditTrainingProgram
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../EditTrainingProgram.fxml"));
            Parent editProgramFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Vis træningsprogram");

            //Transfer the selected program from tableview to EditTrainingProgramController class
            EditTrainingProgramController controller = loader.getController();
            controller.initData(tablePrograms.getSelectionModel().getSelectedItem());

            Scene editProgramScene = new Scene(editProgramFXML);
            stage.setScene(editProgramScene);
            stage.showAndWait();

            clearTable();
            initialize();
        } catch (IOException excp) {
            excp.printStackTrace();
        }
    }

    //Function for delete a program, when button is clicked
    public void deleteProgram(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../DeleteTrainingProgram.fxml"));
            Parent deleteProgramFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Slet træningsprogram");

            DeleteTrainingProgramController cont = loader.getController();
            cont.initData(tablePrograms.getSelectionModel().getSelectedItem());

            Scene deleteProgramScene = new Scene(deleteProgramFXML);
            stage.setScene(deleteProgramScene);
            stage.showAndWait();

            clearTable();
            initialize();

        } catch (IOException excp) {
            excp.printStackTrace();
        }
    }

    //Menubutton functions
    MenuController controller = new MenuController();
    public void menuButtonClick(ActionEvent event) {
        controller.menuNavigation(event);
    }
}
