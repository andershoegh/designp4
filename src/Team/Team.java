package Team;

public class Team {
    int teamId;
    String teamName;
    String teamPic;

    public Team(int teamId, String teamName, String teamPic) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamPic = teamPic;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamPic() {
        return teamPic;
    }

    public void setTeamPic(String teamPic) {
        this.teamPic = teamPic;
    }
}