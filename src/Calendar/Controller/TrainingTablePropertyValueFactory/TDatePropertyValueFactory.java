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

public class TDatePropertyValueFactory<S,T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final String property;

    public TDatePropertyValueFactory(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        Training t = (Training) param.getValue();
        String st = "";


        try {
            DateFormat format1 = new SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH);
            Date localDate = format1.parse(t.getDate());

            DateFormat format2 = new SimpleDateFormat("EEE d/MM/yyyy");
            st = format2.format(localDate).toUpperCase();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        T val = (T) st;
        return new ReadOnlyObjectWrapper<T>(val);
    }
}
