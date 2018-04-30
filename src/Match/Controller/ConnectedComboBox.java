package Match.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConnectedComboBox<T> implements ChangeListener<T> {

    private ObservableList<T> players;
    private List<ChoiceBox<T>> comboBoxList = new ArrayList<>();

    public ConnectedComboBox(ObservableList<T> players){
        this.players = players;
        if (this.players == null){
            this.players = FXCollections.observableArrayList();
        }
    }

    public void addComboBox(ChoiceBox<T> choiceBox){
        comboBoxList.add(choiceBox);
        choiceBox.valueProperty().addListener(this);
        updateSelection();
    }

    public void removeComboBox(ChoiceBox<T> choiceBox){
        comboBoxList.remove(choiceBox);
        choiceBox.valueProperty().removeListener(this);
        updateSelection();
    }

    private boolean updating = false;
    private void updateSelection(){
        if(updating) return;
        updating = true;

        List<T> availableChoices = players.stream().collect(Collectors.toList());
        for (ChoiceBox<T> choiceBox: comboBoxList){
            if(choiceBox.getValue() != null){
                availableChoices.remove(choiceBox.getValue());
            }
        }

        for (ChoiceBox<T> choiceBox: comboBoxList){
            T selectedValue = choiceBox.getValue();
            ObservableList<T> players = choiceBox.getItems();
            players.setAll(availableChoices);

            if (selectedValue != null){
                players.add(selectedValue);
                choiceBox.setValue(selectedValue);
            }
        }

        updating = false;
    }

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue){
        updateSelection();
    }
}
