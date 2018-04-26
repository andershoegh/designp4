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

public class addResultEntryController {
    private ObservableList<Player> availablePlayers = FXCollections.observableArrayList();
    private int availablePlayersFullSize;

    @FXML private ChoiceBox<Player> choiceboxPlayer;
    @FXML private TextField textfieldAmount;
    @FXML private Hyperlink hyperlinkRemove;
    @FXML private GridPane gridPane;

    StringConverter<Player> converter = new StringConverter<Player>() {
        @Override
        public String toString(Player object) {
            return object.getName();
        }

        @Override
        public Player fromString(String string) {
            return null;
        }
    };

    @FXML public void initialize(){

        choiceboxPlayer.setConverter(converter);
        choiceboxPlayer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {
            @Override
            public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
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

    public void initData(ObservableList players){
        availablePlayers = players;
        availablePlayersFullSize = availablePlayers.size();
    }

    private void listenerAvailablePlayers(Player oldValue, Player newValue){
        if(oldValue == null) {
            availablePlayers.remove(newValue);
            System.out.println(newValue.getName() + " fjernes fra listen");
        }else if (oldValue != null && newValue != null) {
            availablePlayers.add(oldValue);
            System.out.println(oldValue.getName() + " tilføjes til listen");
            availablePlayers.remove(newValue);
            System.out.println(newValue.getName() + " fjernes fra listen");
        }
    }

    public void addRowButtonClick(){
        if(gridPane.getRowCount() < availablePlayersFullSize) {
            ChoiceBox<Player> choiceBox = new ChoiceBox();
            choiceBox.setPrefWidth(275);
            TextField textField = new TextField();
            Hyperlink hyperlink = new Hyperlink("fjern");
            hyperlink.setId(Integer.toString(gridPane.getRowCount() + 1));
            hyperlink.getStyleClass().add("delete");
            hyperlink.setPadding(new Insets(0, 0, 0, 14));

            choiceBox.setConverter(converter);
            choiceBox.setItems(availablePlayers);
            choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Player>() {
                @Override
                public void changed(ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                    listenerAvailablePlayers(oldValue, newValue);
                }
            });

            hyperlink.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    removeRowButtonClick(hyperlink);
                }
            });

            gridPane.addRow(gridPane.getRowCount(), choiceBox, textField, hyperlink);
            gridPane.setVgap(5.0);
            System.out.println(gridPane.getRowCount());
        } else {
            System.out.println("No more players in list");
        }
    }

    public void removeRowButtonClick(Hyperlink hyperlink){
        System.out.println("Fjern linje " + hyperlink.getId());
        int i;

        //gridPane
        //gridPane.getChildren().removeIf(node -> gridPane.getRowIndex(node) == Integer.parseInt(hyperlink.getId()));
    }
}
