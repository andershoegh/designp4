package Training.Controller;

import Match.Match;
import Training.Program;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class DurationPropertyValueFactory <S,T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final String property;

    public DurationPropertyValueFactory(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        Program p = (Program) param.getValue();
        String str="";

        if(p.getNumExercises() > 0){
            str = p.getDuration() + " minutter";
        }

        T val = (T) str;
        return new ReadOnlyObjectWrapper<T>(val);
    }
}
