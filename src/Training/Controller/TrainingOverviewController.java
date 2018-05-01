package Training.Controller;

import Controller.MenuController;
import Season.Season;
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
import javafx.util.StringConverter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;


public class TrainingOverviewController {
    private ObservableList<Program> programData = FXCollections.observableArrayList();

    @FXML private TableView<Program> tablePrograms;
    @FXML private TableColumn<?, ?> columnName;
    @FXML private TableColumn<?, ?> columnExercises;
    @FXML private TableColumn<?, ?> columnDuration;


    // Converts seasonData to Season name to be displayed in the Choicebox
    StringConverter<Season> converter = new StringConverter<>() {
        @Override
        public String toString(Season season) {
            return season.getName();
        }

        @Override
        public Season fromString(String id) {
            return null;
        }
    };

    @FXML public void initialize(){
        programData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
    }

    public void clearTable() {
        tablePrograms.getItems().clear();
    }

    //Retrieves the data program class constructor
    private void setCellTable() {
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnExercises.setCellValueFactory(new PropertyValueFactory<>("exercises"));
        columnDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private void loadDataFromDB() {
        Connection conn = SqlConnection.connectToDB();

        try {
            PreparedStatement programStatement = conn.prepareStatement("SELECT * FROM programs");
            ResultSet rsProgram = programStatement.executeQuery();

            while (rsProgram.next()) {
                programData.add(new Program(rsProgram.getString("name"),
                        rsProgram.getString("description"),
                        rsProgram.getInt("exercises"),
                        rsProgram.getInt("duration"))
                );
            }

            SqlConnection.closeConnection();

        } catch (SQLException excp) {
            excp.printStackTrace();
        }

        tablePrograms.setItems(programData);
    }

    public void addProgram(ActionEvent event) {
        //switches from overview to addNewTrainingProgram
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../AddNewTrainingProgram.fxml"));
            Parent addProgramFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Opret nyt træningsprogram");

            Scene addProgramScene = new Scene(addProgramFXML);
            stage.setScene(addProgramScene);
            stage.showAndWait();

            clearTable();
            initialize();

        } catch (IOException excp) {
            excp.printStackTrace();
        }
    }

    public void editProgram(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../EditTrainingProgram.fxml"));
            Parent editProgramFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Vis træningsprogram");

            EditTrainingProgramController controller = loader.getController();
            controller.initData(tablePrograms.getSelectionModel().getSelectedItem());

            Scene editProgramScene = new Scene(editProgramFXML);
            stage.setScene(editProgramScene);
            stage.showAndWait();

            clearTable();
            initialize();

        } catch (IOException excp) {
            excp.printStackTrace();
        } catch (ParseException excp) {
            excp.printStackTrace();
        }
    }

    public void deleteProgram(ActionEvent event) {
        try {
            FXMLLoader loader = new loader();
            loader.setLocation(getClass().getResource("../DeleteTrainingProgram"));
            Parent deleteProgramFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Slet træningsprogram");

            DeleteTrainingProgramController controller = loader.getController();
            controller.initData(tablePrograms.getSelectionModel().getSelectedItem());

            Scene deleteProgramScene = new Scene(deleteProgramFXML);
            stage.setScene(deleteProgramScene);
            stage.showAndWait();

            clearTable();
            initialize();

        } catch (IOException excp) {
            excp.printStackTrace();
        }
    }

    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event) {
        controller.menuNavigation(event);
    }

}
