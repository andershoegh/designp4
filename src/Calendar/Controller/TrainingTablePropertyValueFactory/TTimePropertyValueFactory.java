package Calendar.Controller.TrainingTablePropertyValueFactory;

import Training.Training;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TTimePropertyValueFactory<S,T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final String property;

    public TTimePropertyValueFactory(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        Training t = (Training) param.getValue();
        String st = t.getStartTime() + " - " + t.getEndTime();


        T val = (T) st;
        return new ReadOnlyObjectWrapper<T>(val);
    }
}
