
import javafx.scene.control.*;
import javafx.stage.Stage;
import SQL.SqlConnection;
import javafx.fxml.FXML;
/*
import org.omg.CORBA.INTERNAL;
*/
import java.sql.*;
import java.time.format.DateTimeFormatter;
import static java.lang.String.valueOf;

            } catch (IOException e) {
            //e.printStackTrace();

            Parent wrongInputFXML = FXMLLoader.load(getClass().getResource("../PlayerWrongInput-Pop-up.fxml"));
            Scene wrongInputScene = new Scene (wrongInputFXML);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Forkert indtastning");

            stage.setScene(wrongInputScene);
            stage.showAndWait();
        }
    }

    public void cancelButtonClick(){
        // Closing the window and returning to PlayerList.fxml
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}