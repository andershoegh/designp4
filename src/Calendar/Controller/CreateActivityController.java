package Calendar.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.ParseException;


public class CreateActivityController {

    @FXML private Button cancelButton;

    public void trainingButtonClick(){
        try {

            Parent createEventTrainingFXML = FXMLLoader.load(getClass().getResource("../createActivityTraining.fxml"));
            Scene createActivityTrainingScene = new Scene(createEventTrainingFXML);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Opret træning");

            stage.setScene(createActivityTrainingScene);
            stage.showAndWait();

            stage.close();
            cancelButtonClick();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void matchButtonClick(){
        try {

            Parent createEventMatchFXML = FXMLLoader.load(getClass().getResource("../createActivityMatch.fxml"));
            Scene createActivityMatchScene = new Scene(createEventMatchFXML);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Opret træning");

            stage.setScene(createActivityMatchScene);
            stage.showAndWait();

            stage.close();
            cancelButtonClick();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void miscButtonClick(){
        try {

            Parent createEventMiscFXML = FXMLLoader.load(getClass().getResource("../createActivityMisc.fxml"));
            Scene createActivityMiscScene = new Scene(createEventMiscFXML);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Opret træning");

            stage.setScene(createActivityMiscScene);
            stage.showAndWait();

            stage.close();
            cancelButtonClick();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void cancelButtonClick(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}