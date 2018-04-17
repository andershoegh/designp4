package Lineup.Controller;

import SQL.InnerJoinDB;

public class LineupOverviewController {

    public void printPlayerPositions(){
        InnerJoinDB.extractFromMatchTacticPlayer("players", "name");
        InnerJoinDB.extractFromMatchTacticPlayer("tactics", "name");
        InnerJoinDB.extractFromMatchTacticPlayer("matches", "opponent");
    }
}
