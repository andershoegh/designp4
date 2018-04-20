package Player.Controller;

import Player.Player;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class PhonePropertyValueFactory<S,T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final String property;

    public PhonePropertyValueFactory(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        Player p = (Player) param.getValue();
        Integer i = p.getPhone();
        String st = i.toString();
        st = (st.equals("0"))? "" : st;
        T val = (T) st;
        return new ReadOnlyObjectWrapper<T>(val);
    }
}
