package Match;

import java.util.Date;

public class Match {
    private String opponent;
    private int goalsFor;
    private int goalsAgainst;
    private String season;
    private String date;
    private String time;
    private int id;

    public Match(String opponent, String date) {
        this.date=date;
        this.opponent = opponent;
    }

    public Match(String opponent, int goalsFor, int goalsAgainst, String season, String date, String time, int id) {
        this.opponent = opponent;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.season = season;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public Match(String opponent) {
        this.opponent = opponent;
        }

    public String getOpponent() { return opponent; }
    public void setOpponent(String opponent) { this.opponent = opponent; }

    public int getGoalsFor() { return goalsFor; }
    public void setGoalsFor(int goalsFor) { this.goalsFor = goalsFor; }

    public int getGoalsAgainst() { return goalsAgainst; }
    public void setGoalsAgainst(int goalsAgainst) { this.goalsAgainst = goalsAgainst; }

    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }


    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDate() {
        return date;
    }
}

