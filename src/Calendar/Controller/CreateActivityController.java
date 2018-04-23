package Calendar.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import SQL.SqlConnection;

import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class CreateActivityController {

    private ObservableList<String> typeInputList = FXCollections.observableArrayList(
            "Kamp",
            "Træning",
            "Diverse");

    @FXML private ChoiceBox<String> typeInput;
    @FXML private DatePicker dateInput;
    @FXML private TextField timeInput;
    @FXML private CheckBox locationInput;
    @FXML private TextField notesInput;

    @FXML
    private void initialize(){
        typeInput.setValue("Kamp");
        typeInput.setItems(typeInputList);
    }

    public void acceptButtonClick() throws IOException {
        Connection conn = SqlConnection.connectToDB();
        String sql = "INSERT INTO matches " +
                "(match_id, opponent, goalsFor, goalsAgainst, season, date, time, home_away, note)" +
                "VALUES (null, null, null, null, null,  ?, ?, ?, ?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Passes date input to DB
            String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setString(1, date);

            // Passes time input to DB
            stmt.setString(2, timeInput.getText());

            // Passes location input to DB
            stmt.setInt(3, locationInput.isSelected() ? 1 : 0);

            //Passes notes input to DB
            stmt.setString(4, notesInput.getText());

            stmt.executeUpdate();

            SqlConnection.closeConnection();


        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
//        if(typeInput.getSelectionModel().getSelectedItem() == "Kamp"){
//            passToDB();
//
//        } else if(typeInput.getSelectionModel().getSelectedItem() == "Træning"){
//
//
//        } else if (typeInput.getSelectionModel().getSelectedItem() == "Diverse"){
//
//        }


    }

    public void passToDB() {

    }


}