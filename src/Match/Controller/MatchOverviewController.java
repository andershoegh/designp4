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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import Calendar.Controller.MatchTablePropertyValueFactory.MatchTitelPropertyValueFactory;
import javafx.util.converter.IntegerStringConverter;

public class MatchOverviewController {

    private ObservableList<Match> matchData = FXCollections.observableArrayList();
    private ObservableList<Season> seasonData = FXCollections.observableArrayList();

    @FXML private Label menuTeamName;
    @FXML private TableView<Match> tableMatches;
    @FXML private TableColumn<?, ?> columnOpponent;
    @FXML private TableColumn<Match, Integer> columnGoalsFor;
    @FXML private TableColumn<?, ?> columnGoalsAgainst;
    @FXML private TableColumn<?, ?> columnDate;
    @FXML private TableColumn<?, ?> columnTime;
    @FXML private ChoiceBox<Season> seasonSelector;
    @FXML private Button inputButton;
    @FXML private Button showTacticButton;
    @FXML private Button showResButton;
    @FXML private Button deleteMatchButton;


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
        Date date = new Date();
        seasonData.clear();
        matchData.clear();
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

        tableMatches
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        if(newValue.getConvertedDate().before(date)){
                            inputButton.setDisable(false);
                        }
                        else{
                            inputButton.setDisable(true);
                        }
                        showTacticButton.setDisable(false);
                        deleteMatchButton.setDisable(false);
                    }
                });

        inputButton.setDisable(true);
        showTacticButton.setDisable(true);
        showResButton.setDisable(true);
        deleteMatchButton.setDisable(true);
    }

    // Retrieves data from appropriate player class constructor
    private void setCellTable(){
        columnOpponent.setCellValueFactory(new MatchTitelPropertyValueFactory<>("opponent"));
        columnGoalsFor.setCellValueFactory(new MatchGoalsForPropertyValueFactory<>("goalsFor"));
        columnGoalsAgainst.setCellValueFactory(new MatchGoalsAgainstPropertyValueFactory<>("goalsAgainst"));
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
                        rsMatch.getBoolean("isHome"),
                        rsMatch.getString("note")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!(seasonData.isEmpty())){
            seasonSelector.setItems(seasonData);
            seasonSelector.getSelectionModel().selectLast();
            updateTable(seasonSelector.getValue().getId());
        }


        menuTeamName.setText(SqlConnection.getTeamNameFromDB());

        SqlConnection.closeConnection();
    }


    private void updateTable(int newSeason){
        Collections.sort(matchData, new Comparator<Match>() {
            @Override
            public int compare(Match o1, Match o2) {
                return o1.getConvertedDate().compareTo(o2.getConvertedDate());
            }
        });
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


    public void createMatchButtonClick(){
        try {
            Parent createEventMatchFXML = FXMLLoader.load(getClass().getResource("../../Calendar/createActivityMatch.fxml"));
            Scene createActivityMatchScene = new Scene(createEventMatchFXML);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Opret kamp");
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            stage.setScene(createActivityMatchScene);
            stage.showAndWait();
            stage.close();

            //tableMatches.getItems().clear();
            initialize();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void deleteMatchButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../DeleteMatch.fxml"));
            Parent deleteMatchFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            // Prevents user interaction with other windows while popup is open
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Slet kamp");
            stage.getIcons().add(new Image("file:graphics/ball.png"));

            // Passes selected player info to DeletePlayerController.java
            DeleteMatchController controller = loader.getController();
            controller.initData(tableMatches.getSelectionModel().getSelectedItem());

            Scene deleteMatchScene = new Scene(deleteMatchFXML);
            stage.setScene(deleteMatchScene);
            stage.showAndWait();

            //tableMatches.getItems().clear();
            initialize();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void createSeasonButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../newSeasonPop.fxml"));
            Parent createEventFXML = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene createEventScene = new Scene(createEventFXML);
            stage.setScene(createEventScene);
            stage.showAndWait();
            initialize();

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void showTacticButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../LineupOverview.fxml"));
            Parent lineupFXML = loader.load();

            LineupController lineupController = loader.getController();
            lineupController.initData(tableMatches.getSelectionModel().getSelectedItem());
            //lineupController.loadDataFromDB();

            Stage stage = (Stage) showTacticButton.getScene().getWindow();

            Scene lineupScene = new Scene(lineupFXML);
            stage.setScene(lineupScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
