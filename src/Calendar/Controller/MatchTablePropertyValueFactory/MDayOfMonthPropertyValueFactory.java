package Calendar.Controller.MatchTablePropertyValueFactory;

import Match.Match;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.sqlite.util.StringUtils;

public class MDayOfMonthPropertyValueFactory<S,T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final String property;

    public MDayOfMonthPropertyValueFactory(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        Match m = (Match) param.getValue();
        int iOfSlash = m.getDate().indexOf("/");
        String subString = "";

        if (iOfSlash != -1)
        {
            subString = m.getDate().substring(0 , iOfSlash);
        }

        T val = (T) subString;
        return new ReadOnlyObjectWrapper<T>(val);
    }
}
