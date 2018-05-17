package Match.Controller;

import Match.Match;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class MatchGoalsForPropertyValueFactory<S,T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final String property;

    public MatchGoalsForPropertyValueFactory(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        Match m = (Match) param.getValue();
        String st;
        if(m.getGoalsFor() == -1){
            st = "";
        }
        else{
            st = Integer.toString(m.getGoalsFor());
        }
        T val = (T) st;
        return new ReadOnlyObjectWrapper<T>(val);
    }
}
