
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
            // Inserts data onto the "iceTelephone" field in the database. If there is no data, it will set the string to "null"
            cancelButtonClick();

        } catch(SQLException e) {
            System.out.println(e.getMessage());

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