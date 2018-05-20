
package Calendar.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;



public class CreateActivityController {

    @FXML private Button cancelButton;


    public void trainingButtonClick(ActionEvent event){
        try {

            Parent createEventTrainingFXML = FXMLLoader.load(getClass().getResource("../createActivityTraining.fxml"));
            Scene createActivityTrainingScene = new Scene(createEventTrainingFXML);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Opret træning");
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            stage.setScene(createActivityTrainingScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void matchButtonClick(ActionEvent event){
        try {
            Parent createEventMatchFXML = FXMLLoader.load(getClass().getResource("../createActivityMatch.fxml"));
            Scene createActivityMatchScene = new Scene(createEventMatchFXML);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Opret kamp");
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            stage.setScene(createActivityMatchScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void miscButtonClick(ActionEvent event){
        try {
            Parent createEventMiscFXML = FXMLLoader.load(getClass().getResource("../createActivityMisc.fxml"));
            Scene createActivityMiscScene = new Scene(createEventMiscFXML);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Opret diverse");
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            stage.setScene(createActivityMiscScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void cancelButtonClick(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}