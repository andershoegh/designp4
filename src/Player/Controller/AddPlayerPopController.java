package Player.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sql.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;

public class AddPlayerPopController {

    // This is a connection between the FXML file and the DB, inserting values.
    @FXML
    private TextField nameInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField mailInput;
    @FXML
    private TextField ICEnameInput;
    @FXML
    private TextField ICEphoneInput;
    @FXML
    private TextField positionInput;
    @FXML
    private TextField birthdayInput;
    @FXML
    private TextField healthInput;

}
