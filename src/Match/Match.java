package Match;

import Controller.DeleteAble;
import SQL.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Match implements DeleteAble{
    private String opponent;
    private int goalsFor;
    private int goalsAgainst;
    private String season;
    private String date;
    private String time;
    private int id;
    private String address;
    private int tactic;
    private boolean isHome;

    public Match(String opponent, String date){
        this.opponent = opponent;
        this.date = date;
    }

    public Match(String opponent, int goalsFor, int goalsAgainst, String season,
                 String date, String time, int id, String address, int tactic) {
        this.opponent = opponent;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.season = season;
        this.date = date;
        this.time = time;
        this.id = id;
        this.address = address;
        this.tactic = tactic;
    }

    public Match(String opponent) {
        this.opponent = opponent;
        }

    public Match(int id, String opponent, String date, String time, boolean isHome) {
        this.opponent = opponent;
        this.date = date;
        this.time = time;
        this.isHome = isHome;
        this.id = id;

    }

    public String getOpponent() { return opponent; }
    public void setOpponent(String opponent) { this.opponent = opponent; }

    public int getGoalsFor() { return goalsFor; }
    public void setGoalsFor(int goalsFor) { this.goalsFor = goalsFor; }

    public int getGoalsAgainst() { return goalsAgainst; }
    public void setGoalsAgainst(int goalsAgainst) { this.goalsAgainst = goalsAgainst; }

    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }

    public String getDate() {
        return date;
    }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getTactic() { return tactic; }
    public void setTactic(int tactic) { this.tactic = tactic; }

    public boolean getIsHome() {
        return isHome;
    }

    public void setHome_away(boolean home_away) {
        this.isHome = isHome;
    }


    @Override
    public void delete() {
        try {
            Connection conn = SqlConnection.connectToDB();

            String sql = "DELETE FROM matches WHERE match_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, this.id);
            statement.executeUpdate();

            SqlConnection.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

