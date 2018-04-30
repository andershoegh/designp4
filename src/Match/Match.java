package Match;

import Controller.DeleteAble;
import SQL.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Match implements DeleteAble{
    private String opponent;
    private int goalsFor;
    private int goalsAgainst;
    private int season;
    private String date;
    private String time;
    private int id;
    private int tactic;
    private boolean isHome;

    public Match(String opponent, String date){
        this.opponent = opponent;
        this.date = date;
    }

    public Match(String opponent, int goalsFor, int goalsAgainst, int season,
                 String date, String time, int id, int tactic, boolean isHome) {
        this.opponent = opponent;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.season = season;
        this.date = date;
        this.time = time;
        this.id = id;
        this.tactic = tactic;
        this.isHome = isHome;
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

    public int getSeason() { return season; }
    public void setSeason(int season) { this.season = season; }

    public String getDate() {
        return date;
    }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTactic() { return tactic; }
    public void setTactic(int tactic) { this.tactic = tactic; }

    public boolean getIsHome() {
        return isHome;
    }
    public void setIsHome(boolean isHome) {
        this.isHome = isHome;
    }


    public Date getConvertedDate() {
        DateFormat format = new SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH);
        try {
            return format.parse(this.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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

