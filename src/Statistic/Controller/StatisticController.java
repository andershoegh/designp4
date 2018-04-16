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

    private ObservableList<Player> playerData;

    // Spillere
    @FXML private TableView<Player> stats_players_tableview;
    /*@FXML private TableColumn<?, ?> stats_player_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_players_tablecolumn;
    @FXML private TableColumn<?, ?> stats_player_goals_tablecolumn;
    @FXML private TableColumn<?, ?> stats_player_assists_tablecolumn;

    //Mål
    @FXML private TableView<Player> stats_goals_tableview;
    /*@FXML private TableColumn<?, ?> stats_goals_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_goals_tablecolumn;
    @FXML private TableColumn<?, ?> stats_goals_amount_tablecolumn;

    // Kampe spillet
    @FXML private TableView<Player> stats_match_tableview;
    /*@FXML private TableColumn<?, ?> stats_match_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_match_tablecolumn;
    @FXML private TableColumn<?, ?> stats_match_amount_tablecolumn;

    // Gule kort
    @FXML private TableView<Player> stats_yellowcards_tableview;
    /*@FXML private TableColumn<?, ?> stats_yellowcards_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_yellowcards_tablecolumn;
    @FXML private TableColumn<?, ?> stats_yellowcards_amount_tablecolumn;

    // Røde kort
    @FXML private TableView<Player> stats_redcards_tableview;
    /*@FXML private TableColumn<?, ?> stats_cards_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_redcards_tablecolumn;
    @FXML private TableColumn<?, ?> stats_redcards_amount_tablecolumn;

    // Assists
    @FXML private TableView<Player> stats_assists_tableview;
    /*@FXML private TableColumn<?, ?> stats_assists_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_assists_tablecolumn;
    @FXML private TableColumn<?, ?> stats_assists_amount_tablecolumn;

    // Man of the match
    @FXML private TableView<Player> stats_mofm_tableview;
    /*@FXML private TableColumn<?, ?> stats_mofm_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_mofm_tablecolumn;
    @FXML private TableColumn<?, ?> stats_mofm_amount_tablecolumn;

    // Træninger
    @FXML private TableView<Player> stats_trainings_tableview;
    /*@FXML private TableColumn<?, ?> stats_trainings_number_tablecolumn;*/
    @FXML private TableColumn<?, ?> stats_trainings_tablecolumn;
    @FXML private TableColumn<?, ?> stats_trainings_amount_tablecolumn;


    // Runs when FXML is loaded
    @FXML
    public void initialize() {
        playerData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
    }

    // Retrieves data from appropriate player class constructor
    private void setCellTable() {
        //stats_player_number_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        stats_players_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stats_player_goals_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
        stats_player_assists_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("assists"));
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
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM player");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                playerData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), "00/00/00", rs.getInt("health"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("_id"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // inputting retrieved data from db into table row
        stats_players_tableview.setItems(playerData);
        stats_goals_tableview.setItems(playerData);
        stats_match_tableview.setItems(playerData);
        stats_yellowcards_tableview.setItems(playerData);
        stats_redcards_tableview.setItems(playerData);
        stats_assists_tableview.setItems(playerData);
        stats_mofm_tableview.setItems(playerData);
        stats_trainings_tableview.setItems(playerData);


        SqlConnection.closeConnection();
    }

    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
