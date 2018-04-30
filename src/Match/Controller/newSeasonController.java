package Match.Controller;

import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class newSeasonController {

    private ObservableList<String> choiceBoxList= FXCollections.observableArrayList();

    @FXML private ChoiceBox<String> springFallChoiceBox;
    @FXML private Button cancelButton;

    @FXML
    public void initialize(){
        choiceBoxList.addAll("Forår", "Efterår");
        springFallChoiceBox.setItems(choiceBoxList);
        springFallChoiceBox.setValue("Forår");
    }





    public void cancelButtonClick(){

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }



    /*public void acceptButtonClick(){
        String sql = "INSERT INTO trainings" + "(training_id," +
                "date, start_time, end_time, program_id)" + "VALUES (null, ?, ?, ?, null)";

        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);


            stmt.setString(1, springFallChoiceBox.getValue());

            // Passes date input to DB
            String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setString(2, date);

            // Passes time input to DB
            stmt.setString(3, timeInput.getText());

            // Passes location input to DB
            stmt.setInt(4, locationInput.isSelected() ? 1 : 0);

            //Passes notes input to DB
            stmt.setString(5, notesInput.getText());

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }*/

}
