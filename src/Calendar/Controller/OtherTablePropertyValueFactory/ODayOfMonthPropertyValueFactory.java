package Calendar.Controller.OtherTablePropertyValueFactory;

import Other_Event.Other;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ODayOfMonthPropertyValueFactory<S,T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final String property;

    public ODayOfMonthPropertyValueFactory(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        Other o = (Other) param.getValue();

        int iOfSlash = o.getDate().indexOf("/");
        String subString = "";

        if (iOfSlash != -1)
        {
            subString = o.getDate().substring(0 , iOfSlash) + ".";
        }

        T val = (T) subString;
        return new ReadOnlyObjectWrapper<T>(val);
    }
}
