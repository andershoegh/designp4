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

    // setting FXML IDs
    @FXML
    private TableView<Player> stats_players_tableview;
    /*@FXML
    private TableColumn<?, ?> stats_player_number_tablecolumn;*/
    @FXML
    private TableColumn<?, ?> stats_players_tablecolumn;
    @FXML
    private TableColumn<?, ?> stats_player_goals_tablecolumn;
    @FXML
    private TableColumn<?, ?> stats_player_assists_tablecolumn;

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
        stats_player_goals_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("goalScored"));
        stats_player_assists_tablecolumn.setCellValueFactory(new PropertyValueFactory<>("assist"));
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

        SqlConnection.closeConnection();
    }


    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
