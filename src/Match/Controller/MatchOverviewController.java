package Match.Controller;

import Controller.MenuController;
import Match.Match;
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
    @FXML private TableColumn<?, ?> columnTime;
    @FXML private TableColumn<?, ?> columnOpponent;
    @FXML private TableColumn<?, ?> columnAddress;
    @FXML private TableColumn<?, ?> columnScore;

    @FXML public void initialize(){
        matchData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
    }

    // Retrieves data from appropriate player class constructor
    private void setCellTable(){
        //columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        columnOpponent.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        //columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        //columnScore.setCellValueFactory(new PropertyValueFactory<>("goalsFor"));
    }

    public void loadDataFromDB(){
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM matches");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                matchData.add(new Match(rs.getString("opponent"), rs.getInt("goalsFor"),
                        rs.getInt("goalsAgainst"), rs.getString("season"),
                        rs.getString("date"), rs.getString("time"), rs.getInt("match_id"))
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
