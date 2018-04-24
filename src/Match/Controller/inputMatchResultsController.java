package Match.Controller;

import Match.Match;
import javafx.fxml.FXML;
import javafx.scene.control.*;

class inputMatchResultsController {

    private Match selectedMatch;

    @FXML private Label matchLabel;
    @FXML private Label dateLabel;
    @FXML private ListView listPlayers;
    @FXML private Label labelHomeTeam;
    @FXML private Label labelGuestTeam;
    @FXML private TextField textFieldHomeScore;
    @FXML private TextField textFieldGuestScore;
    @FXML private TextField textFieldNote;

    public void initData(Match match){
        selectedMatch = match;
        if(selectedMatch.getIsHome()) {
            matchLabel.setText(selectedMatch.getOpponent());
        }
    }


   /*
   public static void insertMatch(){

        String season = null, date = null, time = null;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Hvad hedder modpartneren?: ");
        String opponentName = scanner.nextLine();
        if(opponentName.equals("")){
            opponentName = null;
        }

        System.out.print("Hvor meget scorede I?: ");
        String goalsFor = scanner.nextLine();
        if(goalsFor.equals("")){
            goalsFor = null;
        }

        System.out.print("Hvor meget scorede modpartneren?: ");
        String goalsAgainst = scanner.nextLine();
        if(goalsAgainst.equals("")){
            goalsAgainst = null;
        }

        // Preparing to insert the values into database
        String sql = "INSERT INTO matches "
                + "(match_id, opponent, goalsFor, goalsAgainst, season, date, time)" +

                " VALUES (null, ?, ?, ?, ?, ?, ?)";

        // Connecting to the database
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Inserting the values into the database
        stmt.setString(1, opponentName);
        stmt.setString(2, goalsFor);
        stmt.setString(3, goalsAgainst);
        stmt.setString(4, season);
        stmt.setString(5, date);
        stmt.setString(6, time);

        // Updates the database
        stmt.executeUpdate();
    }
    */
}