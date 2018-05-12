package Match;


public class PlayerPos {
    private String playerName;
    private int playerID;
    private int xPos;
    private int yPos;

    public PlayerPos(String player_name, int player_id, int xPos, int yPos) {
        this.playerName = player_name;
        this.playerID = player_id;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerID() {
        return playerID;
    }
}
