package Match.Controller;

import Controller.MenuController;
import Match.Match;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchOverviewController {

    private ObservableList<Match> matchData;
    private ObservableList<String> seasonData;

    @FXML private TableView<Match> tableMatches;
    @FXML private TableColumn<?, ?> columnOpponent;
    @FXML private TableColumn<?, ?> columnGoalsFor;
    @FXML private TableColumn<?, ?> columnGoalsAgainst;
    @FXML private TableColumn<?, ?> columnDate;
    @FXML private TableColumn<?, ?> columnTime;
    @FXML private TableColumn<?, ?> columnAddress;

    @FXML private ComboBox<String> seasonSelector;

    @FXML public void initialize(){
        matchData = FXCollections.observableArrayList();
        seasonData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
    }

    // Retrieves data from appropriate player class constructor
    private void setCellTable(){
        columnOpponent.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        columnGoalsFor.setCellValueFactory(new PropertyValueFactory<>("goalsFor"));
        columnGoalsAgainst.setCellValueFactory(new PropertyValueFactory<>("goalsAgainst"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    public void loadDataFromDB(){
        Connection conn = SqlConnection.connectToDB();

        try {
            PreparedStatement seasonStatement = conn.prepareStatement("SELECT * FROM seasons");
            ResultSet rsSeason = seasonStatement.executeQuery();

            while (rsSeason.next()){ seasonData.addAll(rsSeason.getString("name")); }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement matchStatement = conn.prepareStatement("SELECT * FROM matches");
            ResultSet rsMatch = matchStatement.executeQuery();

            while(rsMatch.next()){
                matchData.add(new Match(rsMatch.getString("opponent"),
                        rsMatch.getInt("goalsFor"),
                        rsMatch.getInt("goalsAgainst"),
                        rsMatch.getString("season_id"),
                        rsMatch.getString("date"),
                        rsMatch.getString("time"),
                        rsMatch.getInt("match_id"),
                        rsMatch.getString("address"),
                        rsMatch.getInt("tactic_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        seasonSelector.setItems(seasonData);
        seasonSelector.getSelectionModel().selectLast();

        tableMatches.setItems(matchData);

        SqlConnection.closeConnection();
    }


    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
