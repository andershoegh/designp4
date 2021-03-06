package Statistic.Controller;

import Controller.DeleteAble;
import Controller.MenuController;
import Player.Player;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticController {

    private Player selectedItem = null;

    @FXML private Label menuTeamName;

    //Mål
    private ObservableList<Player> goalsData;
    @FXML private TableView<Player> stats_goals_tableview;
    @FXML private TableColumn<? , ?> stats_goals_tablecolumn;
    @FXML private TableColumn<?, ?> stats_goals_amount_tablecolumn;

    // Kampe spillet
    private ObservableList<Player> matchData;
    @FXML private TableView<Player> stats_match_tableview;
    @FXML private TableColumn<?, ?> stats_match_tablecolumn;
    @FXML private TableColumn<?, ?> stats_match_amount_tablecolumn;

    // Assists
    private ObservableList<Player> assistsData;
    @FXML private TableView<Player> stats_assists_tableview;
    @FXML private TableColumn<?, ?> stats_assists_tablecolumn;
    @FXML private TableColumn<?, ?> stats_assists_amount_tablecolumn;

    // Man of the match
    private ObservableList<Player> motmData;
    @FXML private TableView<Player> stats_mofm_tableview;
    @FXML private TableColumn<?, ?> stats_mofm_tablecolumn;
    @FXML private TableColumn<?, ?> stats_mofm_amount_tablecolumn;

    // Gule kort
    private ObservableList<Player> yellowData;
    @FXML private TableView<Player> stats_yellowcards_tableview;
    @FXML private TableColumn<?, ?> stats_yellowcards_tablecolumn;
    @FXML private TableColumn<?, ?> stats_yellowcards_amount_tablecolumn;

    // Røde kort
    private ObservableList<Player> redData;
    @FXML private TableView<Player> stats_redcards_tableview;
    @FXML private TableColumn<?, ?> stats_redcards_tablecolumn;
    @FXML private TableColumn<?, ?> stats_redcards_amount_tablecolumn;

    // Runs when FXML is loaded
    @FXML public void initialize() {
        goalsData = FXCollections.observableArrayList();
        matchData = FXCollections.observableArrayList();
        assistsData = FXCollections.observableArrayList();
        motmData = FXCollections.observableArrayList();
        yellowData = FXCollections.observableArrayList();
        redData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();

        stats_goals_tableview
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        stats_match_tableview.getSelectionModel().clearSelection();
                        stats_assists_tableview.getSelectionModel().clearSelection();
                        stats_mofm_tableview.getSelectionModel().clearSelection();
                        stats_yellowcards_tableview.getSelectionModel().clearSelection();
                        stats_redcards_tableview.getSelectionModel().clearSelection();
                    }
                });

        stats_match_tableview
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        stats_goals_tableview.getSelectionModel().clearSelection();
                        stats_assists_tableview.getSelectionModel().clearSelection();
                        stats_mofm_tableview.getSelectionModel().clearSelection();
                        stats_yellowcards_tableview.getSelectionModel().clearSelection();
                        stats_redcards_tableview.getSelectionModel().clearSelection();
                    }
                });


        stats_assists_tableview
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        stats_goals_tableview.getSelectionModel().clearSelection();
                        stats_match_tableview.getSelectionModel().clearSelection();
                        stats_mofm_tableview.getSelectionModel().clearSelection();
                        stats_yellowcards_tableview.getSelectionModel().clearSelection();
                        stats_redcards_tableview.getSelectionModel().clearSelection();
                    }

                });
        stats_mofm_tableview
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        stats_goals_tableview.getSelectionModel().clearSelection();
                        stats_match_tableview.getSelectionModel().clearSelection();
                        stats_assists_tableview.getSelectionModel().clearSelection();
                        stats_yellowcards_tableview.getSelectionModel().clearSelection();
                        stats_redcards_tableview.getSelectionModel().clearSelection();
                    }
                });

        stats_yellowcards_tableview
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        stats_goals_tableview.getSelectionModel().clearSelection();
                        stats_match_tableview.getSelectionModel().clearSelection();
                        stats_assists_tableview.getSelectionModel().clearSelection();
                        stats_mofm_tableview.getSelectionModel().clearSelection();
                        stats_redcards_tableview.getSelectionModel().clearSelection();
                    }

                });
        stats_redcards_tableview
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        stats_goals_tableview.getSelectionModel().clearSelection();
                        stats_match_tableview.getSelectionModel().clearSelection();
                        stats_assists_tableview.getSelectionModel().clearSelection();
                        stats_mofm_tableview.getSelectionModel().clearSelection();
                        stats_yellowcards_tableview.getSelectionModel().clearSelection();
                    }

                });
    }

    // Retrieves data from appropriate player class constructor
    private void setCellTable() {
        stats_goals_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_goals_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
        stats_match_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_match_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("attendedMatches"));
        stats_yellowcards_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_yellowcards_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("yellowCards"));
        stats_redcards_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_redcards_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("redCards"));
        stats_assists_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_assists_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("assists"));
        stats_mofm_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_mofm_amount_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("motm"));
    }

    private void loadDataFromDB() {
        // Tager data fra "goalScored" og sætter det op i Descending order."
        Connection conn = SqlConnection.connectToDB();

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY goalScored DESC, name ASC LIMIT 5");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                goalsData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"),  rs.getInt("health"), rs.getString("birthday"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));

            }
        } catch (SQLException e) {
            System.out.println("--- Problems with loading 'Mål' ---");
        }

        // Tager data fra "attendedMatches" og sætter det op i Descending order."
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY attendedMatches DESC, name ASC LIMIT 5");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                matchData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"),  rs.getInt("health"), rs.getString("birthday"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            System.out.println("--- Problems with loading 'Kampe Spillet' ---");
        }

        // Tager data fra "assist" og sætter det op i Descending order."
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY assist DESC, name ASC LIMIT 5");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                assistsData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"),  rs.getInt("health"), rs.getString("birthday"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            System.out.println("--- Problems with loading 'Assists' ---");
        }

        // Tager data fra "motm" og sætter det op i Descending order."
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY motm DESC, name ASC LIMIT 5");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                motmData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"),  rs.getInt("health"), rs.getString("birthday"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            System.out.println("--- Problems with loading 'Man Of The Match' ---");
        }


        // Tager data fra "yellowCards" og sætter det op i Descending order."
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY yellowCards DESC, name ASC LIMIT 5");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                yellowData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"),  rs.getInt("health"), rs.getString("birthday"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            System.out.println("--- Problems with loading 'Gule Kort' ---");
        }

        // Tager data fra "redCards" og sætter det op i Descending order."
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY redCards DESC, name ASC LIMIT 5");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                redData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"),  rs.getInt("health"), rs.getString("birthday"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id")));
            }
        } catch (SQLException e) {
            System.out.println("--- Problems with loading 'Røde Kort' ---");
        }

        // inputting retrieved data from db into table row
        stats_goals_tableview.setItems(goalsData);
        stats_match_tableview.setItems(matchData);
        stats_assists_tableview.setItems(assistsData);
        stats_mofm_tableview.setItems(motmData);
        stats_yellowcards_tableview.setItems(yellowData);
        stats_redcards_tableview.setItems(redData);

        menuTeamName.setText(SqlConnection.getTeamNameFromDB());

        SqlConnection.closeConnection();
    }

    // Menu buttons navigation
    MenuController controller = new MenuController();
    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
