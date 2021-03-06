package Calendar.Controller;

import Controller.MenuController;
import SQL.SqlConnection;
import Training.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class CreateActivityTrainingController {

    private ObservableList<Program> programList = FXCollections.observableArrayList();

    @FXML private DatePicker dateInput;
    @FXML private TextField startTimeInput;
    @FXML private TextField endTimeInput;
    @FXML private ChoiceBox<Program> programChoicebox;
    @FXML private Button createButton;

    StringConverter<Program> converter = new StringConverter<Program>() {
        @Override public String toString(Program program) { return program.getName(); }
        @Override public Program fromString(String string) { return null; }
    };

    @FXML public void initialize() {
        loadDataFromDB();
        programChoicebox.setConverter(converter);

        dateInput.getEditor().setEditable(false);
    }

    public void loadDataFromDB() {
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement trainingStatement = conn.prepareStatement("SELECT * FROM programs");
            ResultSet trainingRs = trainingStatement.executeQuery();

            while (trainingRs.next()) {
                programList.add(new Program(trainingRs.getInt("program_id"),
                        trainingRs.getString("name"),
                        trainingRs.getString("notes"),
                        trainingRs.getString("duration"),
                        trainingRs.getInt("numExercises")));
            }

            programChoicebox.setItems(programList);

            SqlConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createButtonClick(){
        String sql = "INSERT INTO trainings" + "(training_id," +
                "date, start_time, end_time, program_id)" + "VALUES (null, ?, ?, ?, ?)";
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setString(1, date);
            stmt.setString(2, startTimeInput.getText());
            stmt.setString(3, endTimeInput.getText());

            if(programChoicebox.getSelectionModel().isEmpty()){
                stmt.setInt(4, -1);
            }
            else{
                stmt.setInt(4, programChoicebox.getSelectionModel().getSelectedItem().getId());
            }

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void backButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../createActivityMenu.fxml"));
            Parent createEventFXML = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            Scene createEventScene = new Scene(createEventFXML);
            stage.setScene(createEventScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
