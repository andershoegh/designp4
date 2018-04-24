package Match.Controller;

import Controller.MenuController;
import Match.Match;
import Player.Player;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
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
    private ObservableList<Player> availablePlayers = FXCollections.observableArrayList();

    MenuController controller = new MenuController();

    @FXML private Label matchLabel;
    @FXML private Label dateLabel;
    @FXML private ListView listPlayers;
    @FXML private Label labelHomeTeam;
    @FXML private Label labelGuestTeam;
    @FXML private TextField textFieldHomeScore;
    @FXML private TextField textFieldGuestScore;
    @FXML private TextField textFieldNote;

    public void initData(Match match){
        selectedMatch = match;

        if(selectedMatch.getIsHome()) {
            matchLabel.setText("AAIF vs " + selectedMatch.getOpponent());
            labelHomeTeam.setText("AAIF");
            labelGuestTeam.setText(selectedMatch.getOpponent());
        } else {
            matchLabel.setText(selectedMatch.getOpponent() + " vs AAIF");
            labelHomeTeam.setText(selectedMatch.getOpponent());
            labelGuestTeam.setText("AAIF");
        }

        dateLabel.setText(selectedMatch.getDate());
    }

    public void loadDataFromDB(){
        Connection conn = SqlConnection.connectToDB();

        String sqlQuery = "SELECT *, players.name FROM match_tactic_player INNER JOIN players " +
                "ON match_tactic_player.player_id = players.player_id WHERE match_tactic_player.match_id = " +
                Integer.toString(selectedMatch.getId());

        try {
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);

        ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                availablePlayers.addAll(new Player(rs.getInt("player_id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listPlayers.setItems(availablePlayers);

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
        String sqlMatch = "INSERT INTO matches (goalsFor, goalsAgainst, note) " +
                "VALUES (?, ?, ?) WHERE match_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sqlMatch);

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
        // * Good code goes here *


        SqlConnection.closeConnection();

        controller.sceneChange(event, "../Match/MatchOverview.fxml");
    }

    // Menu navigation
    public void menuButtonClick(ActionEvent event){ controller.menuNavigation(event); }
}