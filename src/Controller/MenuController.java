package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLOutput;

public class MenuController {

    public void menuNavigation(ActionEvent event){
        if(event.getSource().toString().contains("Oversigt")){
            sceneChange(event, "../Overview/Overview.fxml");
        } else if(event.getSource().toString().contains("Kalender")){
            sceneChange(event, "../Calendar/Calendar.fxml");
        } else if(event.getSource().toString().contains("Spillere")){
            sceneChange(event, "../Player/PlayerList.fxml");
        } else if(event.getSource().toString().contains("Kampe")){
            sceneChange(event, "../Match/MatchOverview.fxml");
        } else if(event.getSource().toString().contains("Træning")){
            sceneChange(event, "../Training/TrainingOverview.fxml");
        } else if(event.getSource().toString().contains("Statistik")){
            sceneChange(event, "../Statistic/Statistic.fxml");
        } else if(event.getSource().toString().contains("Diverse")){
            System.out.println("Menupunkt er under udvkling, måske klar i version 2.0");;
        }
    }

    public void sceneChange(ActionEvent event, String path){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent parent = loader.load();

            Scene newScene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void popupSceneChange(String path){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            Parent newFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            Scene addResultScene = new Scene(newFXML);
            stage.setScene(addResultScene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
