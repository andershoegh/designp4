package Lineup.Controller;

import Controller.MenuController;
import SQL.InnerJoinDB;
import SQL.SqlConnection;
import javafx.event.ActionEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LineupOverviewController {

    public static void main(String[] args) {
        ResultSet rsPlayers = InnerJoinDB.extractDataFromForeign("players", "tactic_players", "name");
        ResultSet rsTactics = InnerJoinDB.extractDataFromForeign("tactics", "tactic_players", "name");

        try {
            while(rsPlayers.next()){
                System.out.println(rsPlayers.getString("name"));
            }
            while(rsTactics.next()){
                System.out.println(rsTactics.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SqlConnection.closeConnection();
    }
}
