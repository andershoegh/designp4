package Calendar.Controller;

import SQL.SqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class CreateActivityMiscController {

    @FXML private TextField nameInputField;
    @FXML private DatePicker dateInput;
    @FXML private TextField timeInput;
    @FXML private TextArea notesInput;
    @FXML private Button createButton;

    @FXML public void initialize() {
        dateInput.getEditor().setEditable(false);
    }

    public void createButtonClick() {
        String sql = "INSERT INTO otherEvents " +
                "(other_id, name, date, time, note)" +
                "VALUES (null, ?, ?, ?, ?)";
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, nameInputField.getText());

            // Passes date input to DB
            String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setString(2, date);

            // Passes time input to DB
            stmt.setString(3, timeInput.getText());

            // Passes notes input to DB
            stmt.setString(4, notesInput.getText());

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
