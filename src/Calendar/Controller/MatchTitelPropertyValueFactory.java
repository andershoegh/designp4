package Calendar.Controller;

import Match.Match;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class MatchTitelPropertyValueFactory<S,T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final String property;

    public MatchTitelPropertyValueFactory(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        Match m = (Match) param.getValue();
        String st;
        if(m.isHome_away()){
            st = "AAIF vs " + m.getOpponent();
        }
        else{
            st = m.getOpponent() + "vs AAIF";
        }
        T val = (T) st;
        return new ReadOnlyObjectWrapper<T>(val);
    }
}
