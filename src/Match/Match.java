package Match;

public class Match {
    private String opponent;
    private int goalsFor;
    private int goalsAgainst;
    private String season;
    private String date;
    private String time;
    private int id;
    private String address;
    private int tactic;
    private boolean home_away;

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

    public Match(String opponent, String date, String time, boolean home_away) {
        this.opponent = opponent;
        this.date = date;
        this.time = time;
        this.home_away = home_away;

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

    public boolean isHome_away() {
        return home_away;
    }

    public void setHome_away(boolean home_away) {
        this.home_away = home_away;
    }
}

