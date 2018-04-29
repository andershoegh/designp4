package Calendar.Controller.TrainingTablePropertyValueFactory;

import Training.Training;
        import javafx.beans.property.ReadOnlyObjectWrapper;
        import javafx.beans.value.ObservableValue;
        import javafx.scene.control.TableColumn;
        import javafx.util.Callback;
        import org.sqlite.util.StringUtils;

public class TDayOfMonthPropertyValueFactory<S,T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final String property;

    public TDayOfMonthPropertyValueFactory(String property) {
        this.property = property;
    }

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        Training t = (Training) param.getValue();

        int iOfSlash = t.getDate().indexOf("/");
        String subString = "";

        if (iOfSlash != -1)
        {
            subString = t.getDate().substring(0 , iOfSlash);
        }

        T val = (T) subString;
        return new ReadOnlyObjectWrapper<T>(val);
    }
}

