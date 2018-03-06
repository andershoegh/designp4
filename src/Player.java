// class for all players generated in system
public class Player {
    private String name;
    private String address;
    private String mail;
    private String ICEname;
    private int ICEtelephone; //ved ikke hvordan man får en ICEname kontakt ind - den skal indeholde navn, tlf nr, class for sig i stedet?
    private String position;
    private String birthday;
    private int matchesPlayed;
    private int yellowCards;
    private int redCards;
    private int goalsScored;
    private int assists;
    private int motm;  // man of the match
    private int attendedMatches;
    private int attendedTrainings;

    // constructor
    public Player(String name, String address, String mail, String ICEname, int ICEtelephone, String position, String birthday,
                  int matchesPlayed, int yellowCards, int redCards, int goalsScored, int assists,
                  int motm, int attendedMatches, int attendedTrainings) {
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.ICEname = ICEname;
        this.ICEtelephone = ICEtelephone;
        this.position = position;
        this.birthday = birthday;
        this.matchesPlayed = matchesPlayed;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
        this.goalsScored = goalsScored;
        this.assists = assists;
        this.motm = motm;
        this.attendedMatches = attendedMatches;
        this.attendedTrainings = attendedTrainings;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getICEname() { return ICEname; }
    public void setICEname(String ICEname) { this.ICEname = ICEname; }

    public int getICEtelephone() { return ICEtelephone; }
    public void setICEtelephone(int ICEtelephone) { this.ICEtelephone = ICEtelephone; }

    public String getPosition() { return position; }
    public void setPosition(String position) {
        this.position = position;
    }

    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }
    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getYellowCards() {
        return yellowCards;
    }
    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }
    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public int getGoalsScored() {
        return goalsScored;
    }
    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getAssists() {
        return assists;
    }
    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getMotm() {
        return motm;
    }
    public void setMotm(int motm) {
        this.motm = motm;
    }

    public int getAttendedMatches() {
        return attendedMatches;
    }
    public void setAttendedMatches(int attendedMatches) {
        this.attendedMatches = attendedMatches;
    }

    public int getAttendedTrainings() {
        return attendedTrainings;
    }
    public void setAttendedTrainings(int attendedTrainings) {
        this.attendedTrainings = attendedTrainings;
    }

}


