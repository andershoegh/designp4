package Match.Controller;

import Controller.MenuController;
import Match.Match;
import Player.Player;
import SQL.InnerJoinDB;
import SQL.SqlConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class inputMatchResultsController {

    private Match selectedMatch;
    private int goalsFor;
    private int goalsAgainst;
    private ObservableList<Player> availablePlayers;

    MenuController controller = new MenuController();

    @FXML private Label matchLabel;
    @FXML private Label dateLabel;
    @FXML private ListView listPlayers;
    @FXML private Label labelHomeTeam;
    @FXML private Label labelGuestTeam;
    @FXML private TextField textFieldHomeScore;
    @FXML private TextField textFieldGuestScore;
    @FXML private TextField textFieldNote;

    @FXML public void initialize(){

    }

    public void setCellTable(){
       
    }

    public void initData(Match match){
        selectedMatch = match;

        if(selectedMatch.getIsHome()) {
            matchLabel.setText("AAIF vs" + selectedMatch.getOpponent());
            labelHomeTeam.setText("AAIF");
            labelGuestTeam.setText(selectedMatch.getOpponent());
        } else {
            matchLabel.setText(selectedMatch.getOpponent() + "vs AAIF");
            labelHomeTeam.setText(selectedMatch.getOpponent());
            labelGuestTeam.setText("AAIF");
        }

        dateLabel.setText(selectedMatch.getDate());
    }

    public void loadDataFromDB(){
        SqlConnection.connectToDB();
        ResultSet rs = InnerJoinDB.extractDataFromForeign("players", "match_tactic_player", "name");

        try {
            while(rs.next()){
                System.out.println(rs.getString("player_id"));
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SqlConnection.closeConnection();
    }

    public void acceptButtonClick(ActionEvent event){

        if(selectedMatch.getIsHome()){
            goalsFor = Integer.parseInt(textFieldHomeScore.getText());
            goalsAgainst = Integer.parseInt(textFieldGuestScore.getText());
        } else {
            goalsAgainst = Integer.parseInt(textFieldHomeScore.getText());
            goalsFor = Integer.parseInt(textFieldGuestScore.getText());
        }

        Connection conn = SqlConnection.connectToDB();

        // Inserting data into matches table
        try {
        String sqlMatch = "INSERT INTO matches " +
                "(goalsFor, goalsAgainst, note)" +
                " VALUES (?, ?, ?)" +
                "WHERE ? = match_id";

            // Connecting to the database
            PreparedStatement stmt = conn.prepareStatement(sqlMatch);

            // Inserting the values into the database
            stmt.setInt(1, goalsFor);
            stmt.setInt(2, goalsAgainst);
            stmt.setString(3, textFieldNote.getText());
            stmt.setInt(4, selectedMatch.getId());

            // Updates the database
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Inserting data into players table


        SqlConnection.closeConnection();

        controller.sceneChange(event, "../Match/MatchOverview.fxml");
    }

    public void menuButtonClick(ActionEvent event){ controller.menuNavigation(event); }
}