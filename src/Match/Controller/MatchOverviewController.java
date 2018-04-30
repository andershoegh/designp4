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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class MatchOverviewController {

    private ObservableList<Match> matchData = FXCollections.observableArrayList();
    private ObservableList<Season> seasonData = FXCollections.observableArrayList();

    @FXML private TableView<Match> tableMatches;
    @FXML private TableColumn<?, ?> columnOpponent;
    @FXML private TableColumn<?, ?> columnGoalsFor;
    @FXML private TableColumn<?, ?> columnGoalsAgainst;
    @FXML private TableColumn<?, ?> columnDate;
    @FXML private TableColumn<?, ?> columnTime;
    @FXML private ChoiceBox<Season> seasonSelector;
    @FXML private Button inputButton;

    // Converts seasonData to Season name to be displayed in the Choicebox
    StringConverter<Season> converter = new StringConverter<>() {
        @Override public String toString(Season season) {
            return season.getName();
        }
        @Override public Season fromString(String id) {
            return null;
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

    public void loadDataFromDB(){
        Connection conn = SqlConnection.connectToDB();

        try {
            PreparedStatement seasonStatement = conn.prepareStatement("SELECT * FROM seasons");
            ResultSet rsSeason = seasonStatement.executeQuery();

            while (rsSeason.next()){ seasonData.addAll(new Season(rsSeason.getString("name"), rsSeason.getInt("season_id"))); }
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
                        rsMatch.getInt("season_id"),
                        rsMatch.getString("date"),
                        rsMatch.getString("time"),
                        rsMatch.getInt("match_id"), 0,
                        rsMatch.getBoolean("isHome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        seasonSelector.setItems(seasonData);

        SqlConnection.closeConnection();

        seasonSelector.getSelectionModel().selectLast();
        updateTable(seasonSelector.getValue().getId());
    }

    private void updateTable(int newSeason){
        tableMatches.setItems(matchData.filtered(match -> match.getSeason() == newSeason));
    }

    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }

    public void inputMatchResults(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../inputMatchResults.fxml"));
            Parent inputResultsFXML = loader.load();

            inputMatchResultsController matchResultsController = loader.getController();
            matchResultsController.initData(tableMatches.getSelectionModel().getSelectedItem());
            matchResultsController.loadDataFromDB();

            Stage stage = (Stage) inputButton.getScene().getWindow();

            Scene inputResultsScene = new Scene(inputResultsFXML);
            stage.setScene(inputResultsScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
