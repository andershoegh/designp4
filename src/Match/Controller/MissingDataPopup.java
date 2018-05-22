package Match.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MissingDataPopup {

    private String message;
    private static String missingData;
    private static boolean closeWindow = true;

    @FXML private Label labelMessage;
    @FXML private Button acceptButton;
    @FXML private HBox hBox;

    @FXML public void initialize(){
        switch (missingData){
            case "missing score":
                message = "Kampens resultat er ikke indtastet." + "\n" + "Indtast venligst resultat for at gemme.";
                acceptButton.setOnAction(event -> cancelButtonClick(event));

                break;
            case "missing goalscorer":
                message = "Valgte målscorer matcher ikke det indtastede resultat." + "\n" + "Tryk fortryd for at gå tilbage og vælge målscorer.";
                acceptButton.setVisible(false);
                //acceptButton.setOnAction(event -> acceptButtonClick(event));

                Button cancelButton = new Button("Fortryd");
                cancelButton.setOnAction(event -> cancelButtonClick(event));
                cancelButton.getStyleClass().add("overviewButtons");

                hBox.setSpacing(5.0);
                hBox.getChildren().add(cancelButton);

                break;
        }

        labelMessage.setText(message);
    }

    public void initData(String warning){
        missingData = warning;
    }

    public void acceptButtonClick(ActionEvent event){
        closeWindow = true;

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cancelButtonClick(ActionEvent event){
        closeWindow = false;

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public Boolean getCloseWindow(){ return closeWindow; }
}
