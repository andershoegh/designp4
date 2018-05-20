package Calendar.Controller.MatchTablePropertyValueFactory;

import Match.Match;
import SQL.SqlConnection;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.sql.Connection;

public class MatchTitelPropertyValueFactory<S,T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
    private final String property;

    public MatchTitelPropertyValueFactory(String property) {
        this.property = property;
    }



    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        SqlConnection.connectToDB();
        Match m = (Match) param.getValue();
        String st;
        if(m.getIsHome()){
            st = SqlConnection.getTeamNameFromDB() + " vs " + m.getOpponent();
        }
        else{
            st = m.getOpponent() + " vs " + SqlConnection.getTeamNameFromDB();
        }
        T val = (T) st;
        SqlConnection.closeConnection();
        return new ReadOnlyObjectWrapper<T>(val);
    }
}
