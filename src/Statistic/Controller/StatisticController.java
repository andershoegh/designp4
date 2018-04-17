package Statistic.Controller;

import Controller.MenuController;
import Player.Player;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticController {

    private ObservableList<Player> goalsData;
    //Mål
    @FXML private TableView<Player> stats_goals_tableview;
    /*@FXML private TableColumn<?, ?> stats_goals_number_tablecolumn;*/
    @FXML private TableColumn<? , ?> stats_goals_tablecolumn;
    @FXML private TableColumn<?, ?> stats_goals_amount_tablecolumn;

    private ObservableList<Player> matchData;
    // Kampe spillet
    @FXML private TableView<Player> stats_match_tableview;
    /*@FXML private TableColumn<?, ?> stats_match_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_match_tablecolumn;
    @FXML private TableColumn<?, ?> stats_match_amount_tablecolumn;

    private ObservableList<Player> assistsData;
    // Assists
    @FXML private TableView<Player> stats_assists_tableview;
    /*@FXML private TableColumn<?, ?> stats_assists_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_assists_tablecolumn;
    @FXML private TableColumn<?, ?> stats_assists_amount_tablecolumn;

    private ObservableList<Player> motmData;
    // Man of the match
    @FXML private TableView<Player> stats_mofm_tableview;
    /*@FXML private TableColumn<?, ?> stats_mofm_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_mofm_tablecolumn;
    @FXML private TableColumn<?, ?> stats_mofm_amount_tablecolumn;

    private ObservableList<Player> trainingsData;
    // Træninger
    @FXML private TableView<Player> stats_trainings_tableview;
    /*@FXML private TableColumn<?, ?> stats_trainings_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_trainings_tablecolumn;
    @FXML private TableColumn<?, ?> stats_trainings_amount_tablecolumn;

    private ObservableList<Player> yellowData;
    // Gule kort
    @FXML private TableView<Player> stats_yellowcards_tableview;
    /*@FXML private TableColumn<?, ?> stats_yellowcards_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_yellowcards_tablecolumn;
    @FXML private TableColumn<?, ?> stats_yellowcards_amount_tablecolumn;

    private ObservableList<Player> redData;
    // Røde kort
    @FXML private TableView<Player> stats_redcards_tableview;
    /*@FXML private TableColumn<?, ?> stats_cards_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_redcards_tablecolumn;
    @FXML private TableColumn<?, ?> stats_redcards_amount_tablecolumn;


    // Runs when FXML is loaded
    @FXML
    public void initialize() {
        goalsData = FXCollections.observableArrayList();
        matchData = FXCollections.observableArrayList();
        assistsData = FXCollections.observableArrayList();
        motmData = FXCollections.observableArrayList();
        trainingsData = FXCollections.observableArrayList();
        yellowData = FXCollections.observableArrayList();
        redData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
    }

    // Retrieves data from appropriate player class constructor
    private void setCellTable() {
        //stats_goals_number_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        stats_goals_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_goals_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
        //stats_goals_number_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        stats_match_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_match_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("attendedMatches"));
        //stats_yellowcards_number_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        stats_yellowcards_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_yellowcards_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("yellowCards"));
        //stats_cards_number_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        stats_redcards_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_redcards_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("redCards"));
        //stats_assists_number_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        stats_assists_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_assists_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("assists"));
        //stats_mofm_number_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        stats_mofm_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_mofm_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("motm"));
        //stats_trainings_number_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        stats_trainings_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_trainings_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("attendedTrainings"));
    }

    private void loadDataFromDB() {
        // Tager data fra "goalScored" og sætter det op i Descending order."
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY goalScored DESC, name ASC  ");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                goalsData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), "00/00/00", rs.getInt("health"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tager data fra "attendedMatches" og sætter det op i Descending order."
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY attendedMatches DESC, name ASC  ");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                matchData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), "00/00/00", rs.getInt("health"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tager data fra "assist" og sætter det op i Descending order."
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY assist DESC, name ASC  ");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                assistsData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), "00/00/00", rs.getInt("health"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tager data fra "motm" og sætter det op i Descending order."
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY motm DESC, name ASC ");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                motmData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), "00/00/00", rs.getInt("health"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tager data fra "attendedTrainings" og sætter det op i Descending order."
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY attendedTrainings DESC, name ASC  ");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                trainingsData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), "00/00/00", rs.getInt("health"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tager data fra "yellowCards" og sætter det op i Descending order."
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY yellowCards DESC, name ASC  ");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                yellowData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), "00/00/00", rs.getInt("health"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tager data fra "redCards" og sætter det op i Descending order."
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY redCards DESC, name ASC  ");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                redData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), "00/00/00", rs.getInt("health"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // inputting retrieved data from db into table row
        stats_goals_tableview.setItems(goalsData);
        stats_match_tableview.setItems(matchData);
        stats_assists_tableview.setItems(assistsData);
        stats_mofm_tableview.setItems(motmData);
        stats_trainings_tableview.setItems(trainingsData);
        stats_yellowcards_tableview.setItems(yellowData);
        stats_redcards_tableview.setItems(redData);


        SqlConnection.closeConnection();
    }

    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
