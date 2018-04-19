package Match.Controller;

import Controller.MenuController;
import Match.Match;
import SQL.InnerJoinDB;
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

public class MatchOverviewController {

    private ObservableList<Match> matchData;

    @FXML private TableView<Match> tableMatches;
    @FXML private TableColumn<?, ?> columnOpponent;
    @FXML private TableColumn<?, ?> columnScore;
    @FXML private TableColumn<?, ?> columnGoalsFor;
    @FXML private TableColumn<?, ?> columnGoalsAgainst;
    @FXML private TableColumn<?, ?> columnDate;
    @FXML private TableColumn<?, ?> columnTime;
    @FXML private TableColumn<?, ?> columnAddress;
    @FXML private TableColumn<?, ?> columnTactic;

    @FXML public void initialize(){
        matchData = FXCollections.observableArrayList();
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
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM matches");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                matchData.add(new Match(rs.getString("opponent"), rs.getInt("goalsFor"),
                        rs.getInt("goalsAgainst"), rs.getString("season"),
                        rs.getString("date"), rs.getString("time"), rs.getInt("match_id"),
                        rs.getString("address"),0)
                );
            }

            SqlConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableMatches.setItems(matchData);
    }


    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
