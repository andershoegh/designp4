package Calendar.Controller;

import SQL.SqlConnection;
import Training.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditActivityTrainingController {

    private static Training selectedTraining;
    private ObservableList<Program> programList = FXCollections.observableArrayList();

    @FXML private DatePicker dateInput;
    @FXML private TextField startTimeInput;
    @FXML private TextField endTimeInput;
    @FXML private ChoiceBox<Program> programChoicebox;
    @FXML private Button editButton;

    StringConverter<Program> converter = new StringConverter<>() {
        @Override public String toString(Program program) { return program.getName(); }
        @Override public Program fromString(String string) { return null; }
    };

    @FXML public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate selectedDate = LocalDate.parse(selectedTraining.getDate(), formatter);
        dateInput.setValue(selectedDate);
        dateInput.getEditor().setEditable(false);

        startTimeInput.setText(selectedTraining.getStartTime());
        endTimeInput.setText(selectedTraining.getEndTime());

        loadDataFromDB();
        programChoicebox.setConverter(converter);
    }

    public void initData(Training training) {
        selectedTraining = training;
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
            if(selectedTraining.getProgramID() != -1){
                programChoicebox.setValue(programList.filtered(program -> program.getId() == selectedTraining.getProgramID()).get(0));
            }

            SqlConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editButtonClick(){
        String sql = "UPDATE trainings SET date=?, start_time=?, end_time=?, program_id=? WHERE training_id = ?";
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setString(1, date);

            stmt.setString(2, startTimeInput.getText());

            stmt.setString(3, endTimeInput.getText());

            if(programChoicebox.getSelectionModel().isEmpty()){
                stmt.setInt(4,-1);
            }
            else{
                stmt.setInt(4, programChoicebox.getSelectionModel().getSelectedItem().getId());
            }

            stmt.setInt(5, selectedTraining.getId());

            stmt.executeUpdate();

            SqlConnection.closeConnection();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }
}
