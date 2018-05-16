package Overview.Controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import SQL.SqlConnection;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.*;

public class TeamController {

    // This is a connection between the FXML file and the DB, inserting values.
    @FXML private TextField teamInput;
    @FXML private Button createTeam;
    @FXML private Button cancelButton;

    @FXML
    public void initialize() {
    }

    public void acceptButtonClick() throws IOException {
        Connection conn = SqlConnection.connectToDB();
        String sql = "UPDATE team SET team_id = 1, " +
                "team_name = ?, team_image = null ";

        // Connecting to the database
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Inserts data into the "name" field in the database. If there is no data, it will set the string to "null"
            if (teamInput.getText().equals("")) {
                stmt.setString(1, null);
            } else {
                stmt.setString(1, teamInput.getText());
            }

            // Updates the database
            stmt.executeUpdate();

            // Closes the connected database
            SqlConnection.closeConnection();
            cancelButtonClick();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../PlayerWrongInput-Pop-up.fxml"));
            Parent wrongInputFXML = loader.load();
            Scene wrongInputScene = new Scene(wrongInputFXML);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Forkert input");
            stage.setScene(wrongInputScene);
            stage.showAndWait();
            stage.close();
        }
    }

    public void cancelButtonClick(){
        // Closing the program
        System.exit(0);
    }
}

