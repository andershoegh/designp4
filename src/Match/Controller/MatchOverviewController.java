package Match.Controller;

import Controller.MenuController;
import Match.Match;
import SQL.SqlConnection;
import Season.Season;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

public class MatchOverviewController {

    private ObservableList<Match> matchData = FXCollections.observableArrayList();
    private ObservableList<Season> seasonData = FXCollections.observableArrayList();
    private ObservableList<Match> filtMatchData = FXCollections.observableArrayList();

    @FXML private TableView<Match> tableMatches;
    @FXML private TableColumn<?, ?> columnOpponent;
    @FXML private TableColumn<?, ?> columnGoalsFor;
    @FXML private TableColumn<?, ?> columnGoalsAgainst;
    @FXML private TableColumn<?, ?> columnDate;
    @FXML private TableColumn<?, ?> columnTime;

    @FXML private ChoiceBox<Season> seasonSelector;

    // Converts seasonData to Season name to be displayed in the Choicebox
    StringConverter<Season> converter = new StringConverter<>() {
        @Override
        public String toString(Season season) {
            return season.getName();
        }

        @Override
        public Season fromString(String id) {
            return seasonData.stream().filter(season -> id.equals(season.getId())).collect(Collectors.toList()).get(0);
        }
    };

    @FXML public void initialize(){
        setCellTable();
        loadDataFromDB();
        seasonSelector.setConverter(converter);

        seasonSelector.getSelectionModel()
                .selectedItemProperty()
                .addListener(new ChangeListener<Season>() {
                    @Override
                    public void changed(ObservableValue<? extends Season> observable, Season oldValue, Season newValue) {
                        if(newValue != null){
                            System.out.println(newValue.getName());
                            updateTable(newValue.getId());
                        }
                    }
                });
    }

    // Retrieves data from appropriate player class constructor
    private void setCellTable(){
        columnOpponent.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        columnGoalsFor.setCellValueFactory(new PropertyValueFactory<>("goalsFor"));
        columnGoalsAgainst.setCellValueFactory(new PropertyValueFactory<>("goalsAgainst"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
    
    private void loadDataFromDB(){
        Connection conn = SqlConnection.connectToDB();

        // loads seasons data
        try {
            PreparedStatement seasonStatement = conn.prepareStatement("SELECT * FROM seasons");
            ResultSet rsSeason = seasonStatement.executeQuery();

            while (rsSeason.next()){
                seasonData.addAll(new Season(rsSeason.getInt("season_id"),
                        rsSeason.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // loads matches data
        try {
            PreparedStatement matchStatement = conn.prepareStatement("SELECT * FROM matches");
            ResultSet rsMatch = matchStatement.executeQuery();

            while(rsMatch.next()){
                matchData.add(new Match(rsMatch.getString("opponent"),
                        rsMatch.getInt("goalsFor"),
                        rsMatch.getInt("goalsAgainst"),
                        rsMatch.getInt("season_id"),
                        rsMatch.getString("date"),
                        rsMatch.getString("time"),
                        rsMatch.getInt("match_id"),
                        rsMatch.getInt("tactic_id"),
                        rsMatch.getBoolean("home_away")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        seasonSelector.setItems(seasonData);

        SqlConnection.closeConnection();

        seasonSelector.getSelectionModel().selectLast();
        updateTable(seasonSelector.getValue().getId());
    }

    public void seasonSelectorClicked(){
    }

    private void updateTable(int newSeason){
        filtMatchData = matchData.filtered(match -> match.getSeason() == newSeason);
        tableMatches.setItems(filtMatchData);
    }

    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchOverviewController that = (MatchOverviewController) o;
        return Objects.equals(seasonData, that.seasonData);
    }

    @Override
    public int hashCode() {

        return Objects.hash(seasonData);
    }
}
