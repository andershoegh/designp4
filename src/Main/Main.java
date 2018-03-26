package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../Player/AddPlayer.fxml"));
        primaryStage.setTitle("Coach meister 9000");
        primaryStage.setScene(new Scene(root, 800, 1209));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
