package Overview.Controller;

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
    @FXML private Button createTeamButton;

    public void acceptButtonClick() throws IOException {
        Connection conn = SqlConnection.connectToDB();
        String sql = "UPDATE team SET team_id = 1, " +
                "team_name = ?";

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
            Stage stage = (Stage) createTeamButton.getScene().getWindow();
            stage.close();

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

