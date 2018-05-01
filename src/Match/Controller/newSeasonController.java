package Match.Controller;

import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class newSeasonController {

    private ObservableList<String> choiceBoxList= FXCollections.observableArrayList();

    @FXML private ChoiceBox<String> springFallChoiceBox;
    @FXML private TextField yearTextField;
    @FXML private Button cancelButton;
    @FXML private Button createButton;

    @FXML
    public void initialize(){
        choiceBoxList.addAll("Forår", "Efterår");
        springFallChoiceBox.setItems(choiceBoxList);
        springFallChoiceBox.setValue("Forår");
    }



    public void acceptButtonClick(){
        String sql = "INSERT INTO seasons" + "(season_id," +
                "name)" + "VALUES (null, ?)";

        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, springFallChoiceBox.getValue() + " " + yearTextField.getText());

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



    public void cancelButtonClick(){

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

}
