package Match.Controller;

import Player.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class addResultEntryController {
    private static ObservableList<Player> availablePlayers = FXCollections.observableArrayList();
    private static int availablePlayersFullSize;
    private int numberOfRows = 1;

    @FXML private ChoiceBox<Player> choiceboxPlayer;
    @FXML private TextField textfieldAmount;
    @FXML private Hyperlink hyperlinkRemove;
    @FXML private GridPane gridPane;

    StringConverter<Player> converter = new StringConverter<Player>() {
        @Override public String toString(Player object) { return object.getName(); }
        @Override public Player fromString(String string) { return null; }
    };

    @FXML public void initialize(){
        choiceboxPlayer.setItems(availablePlayers);
        choiceboxPlayer.setConverter(converter);
        //choiceboxPlayer.getStyleClass().add("greenButton");
        choiceboxPlayer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                choiceboxPlayer.setValue(observable.getValue());
                listenerAvailablePlayers(oldValue, newValue);
            }
        });

        hyperlinkRemove.setId("1");
        hyperlinkRemove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeRowButtonClick(hyperlinkRemove);
            }
        });
    }

    public static void initData(ObservableList<Player> players){
        availablePlayers.addAll(players);
        availablePlayersFullSize = availablePlayers.size();
    }

    private void listenerAvailablePlayers(Player oldValue, Player newValue){

        Player oV = oldValue, nV = newValue;

        System.out.println("oldValue : " + oV);
        System.out.println("newValue : " + nV);

        if(oV == null){
            availablePlayers.remove(nV);
            System.out.println("if");
        } else if(nV == null) {
            availablePlayers.remove(oV);
            System.out.println("if else");
        } else {
            availablePlayers.add(oV);
            availablePlayers.remove(nV);
            System.out.println("else");
        }
    }

    public void addRowButtonClick(){
        if(availablePlayers.size() > 0) {
            ChoiceBox<Player> choiceBox = new ChoiceBox<>();
            choiceBox.setItems(availablePlayers);
            choiceBox.setConverter(converter);
            choiceBox.getStyleClass().add("greenButton");
            choiceBox.setPrefWidth(275);
            choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {
                @Override
                public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                    choiceBox.setValue(newValue);
                    listenerAvailablePlayers(oldValue, newValue);
                }
            });

            TextField textField = new TextField();

            Hyperlink hyperlink = new Hyperlink("fjern");
            hyperlink.setId(Integer.toString(gridPane.getRowCount() + 1));
            hyperlink.getStyleClass().add("delete");
            hyperlink.setPadding(new Insets(0, 0, 0, 14));
            hyperlink.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    removeRowButtonClick(hyperlink);
                }
            });

            gridPane.addRow(gridPane.getRowCount(), choiceBox, textField, hyperlink);
            gridPane.setVgap(5.0);

            numberOfRows++;
        } else {
            System.out.println("No more players in list");
        }
    }

    public void removeRowButtonClick(Hyperlink hyperlink){
        System.out.println("Fjern linje " + hyperlink.getId());

        //gridPane.getChildren().remove(3,6);
        //gridPane.getChildren().removeIf(node -> gridPane.getRowIndex(node) == Integer.parseInt(hyperlink.getId()));

        //numberOfRows--;
    }
}
