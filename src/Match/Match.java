package Match;

public class Match {
    private int goalsFor;
    private int goalsAgainst;
    private int redCards;
    private int yellowCards;
    private String playerScored; // playerScored is for the coach, to state which Player scored a goal this Match.
    private String opponentName;

    // getter and setters
    public int getGoalsFor() { return goalsFor; }
    public void setGoalsFor(int goalsFor) { this.goalsFor = goalsFor; }

    public int getGoalsAgainst() { return goalsAgainst; }
    public void setGoalsAgainst(int goalsAgainst) { this.goalsAgainst = goalsAgainst; }

    public int getRedCards() { return redCards; }
    public void setRedCards(int redCards) { this.redCards = redCards; }

    public int getYellowCards() { return yellowCards; }
    public void setYellowCards(int yellowCards) { this.yellowCards = yellowCards; }

    public String getPlayerScored() { return playerScored; }
    public void setPlayerScored(String playerScored) { this.playerScored = playerScored; }

    public String getOpponentName() { return opponentName; }
    public void setOpponentName(String opponentName) { this.opponentName = opponentName; }

    // Constructor
    public Match(int goalsFor, int goalsAgainst, int redCards, int yellowCards, String playerScored, String opponentName) {
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.redCards = redCards;
        this.yellowCards = yellowCards;
        this.playerScored = playerScored;
        this.opponentName = opponentName;
    }
}