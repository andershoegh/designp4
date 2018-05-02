package Match.Controller;

import Player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class addResultEntryController {
    private static ObservableList<Player> availablePlayers = FXCollections.observableArrayList();
    private static String selectedTable;

    @FXML private Label labelTitle;
    @FXML private ChoiceBox<Player> choiceboxPlayer;
    @FXML private GridPane gridPane;

    StringConverter<Player> converter = new StringConverter<>() {
        @Override public String toString(Player object) { return object.getName(); }
        @Override public Player fromString(String string) { return null; }
    };

    @FXML public void initialize(){
        choiceboxPlayer.setItems(availablePlayers);
        choiceboxPlayer.setConverter(converter);
        choiceboxPlayer.getStyleClass().add("greenButton");

        switch (selectedTable) {
            case "Goals":
                labelTitle.setText("Tilføj målscorer");
                addTextField();
                break;
            case "Assists":
                labelTitle.setText("Tilføj assist");
                addTextField();
                break;
            case "Yellow":
                labelTitle.setText("Tilføj gult kort");
                addChoiceBox();
                break;
            case "Red":
                labelTitle.setText("Tilføj rødt kort");
                addChoiceBox();
                break;
            default:
                break;
        }
    }

    public static void initData(ObservableList<Player> players, String id){
        availablePlayers.clear();
        availablePlayers.addAll(players);
        selectedTable = id;
    }

    public void addTextField(){
        TextField textFieldAmount = new TextField();
        textFieldAmount.setPrefWidth(32.0);
        textFieldAmount.setPrefHeight(35.0);

        gridPane.add(textFieldAmount, 1, 1);
    }

    public void addChoiceBox(){
        ChoiceBox<Integer> choiceBoxAmount = new ChoiceBox<>();
        choiceBoxAmount.getItems().addAll(1, 2);
        choiceBoxAmount.setMinWidth(32.0);
        choiceBoxAmount.setPrefHeight(35.0);
        choiceBoxAmount.getStyleClass().add("greenButton");

        gridPane.add(choiceBoxAmount, 1, 1);
    }

    public void addButtonClick(ActionEvent event){
        if (choiceboxPlayer.getValue() != null && gridPane.getChildren().get(3) != null) {
            inputMatchResultsController controller = new inputMatchResultsController();

            if (selectedTable.equals("Goals") || selectedTable.equals("Assists")) {
                controller.storeSelectedEntry(selectedTable,
                        choiceboxPlayer.getValue(),
                        Integer.parseInt(((TextField) gridPane.getChildren().get(3)).getText()));
            } else {
                controller.storeSelectedEntry(selectedTable, choiceboxPlayer.getValue(), Integer.parseInt(((ChoiceBox) gridPane.getChildren().get(3)).getValue().toString()));
            }
        }

        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.close();
    }
}
