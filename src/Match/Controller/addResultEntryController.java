package Match.Controller;

import Player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class addResultEntryController {
    private static ObservableList<Player> availablePlayers = FXCollections.observableArrayList();
    private static String selectedTable;

    @FXML private ChoiceBox<Player> choiceboxPlayer;
    @FXML private TextField textfieldAmount;

    StringConverter<Player> converter = new StringConverter<>() {
        @Override public String toString(Player object) { return object.getName(); }
        @Override public Player fromString(String string) { return null; }
    };

    @FXML public void initialize(){
        choiceboxPlayer.setItems(availablePlayers);
        choiceboxPlayer.setConverter(converter);
        choiceboxPlayer.getStyleClass().add("greenButton");
    }

    public static void initData(ObservableList<Player> players, String id){
        availablePlayers.clear();
        availablePlayers.addAll(players);
        selectedTable = id;
    }

    public void addButtonClick(ActionEvent event){
        if (choiceboxPlayer.getValue() != null && textfieldAmount.getText() != null) {
            inputMatchResultsController controller = new inputMatchResultsController();
            controller.storeSelectedEntry(selectedTable,
                    choiceboxPlayer.getValue(),
                    Integer.parseInt(textfieldAmount.getText()));
        }
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.close();
    }
}
