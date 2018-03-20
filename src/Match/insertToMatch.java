package Match;

import java.sql.*;
import java.util.Scanner;

class insertToMatch {

    public static void insertMatch(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

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

        System.out.print("Hvor mange røde kort fik I?: ");
        String redCards = scanner.nextLine();
        if(redCards.equals("")){
            redCards = null;
        }

        System.out.print("Hvor mange gule kort fik I?: ");
        String yellowCards = scanner.nextLine();
        if(yellowCards.equals("")){
            yellowCards = null;
        }

        System.out.print("Hvem scorede? (Skriv , hvis der var flere): ");
        String playerScored = scanner.nextLine();
        if(playerScored.equals("")){
            playerScored = null;
        }

        System.out.print("Hvad hedder modpartneren?: ");
        String opponentName = scanner.nextLine();
        if(opponentName.equals("")){
            opponentName = null;
        }


        // Preparing to insert the values into database
        String sql = "INSERT INTO match "
                + "(_id, goalsFor, goalsAgainst, redCards, yellowCards, playerScored, opponentName)" +

                " VALUES (null, ?, ?, ?, ?, ?, ?)";

        // Connecting to the database
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Inserting the values into the database
        stmt.setString(1, goalsFor);
        stmt.setString(2, goalsAgainst);
        stmt.setString(3, redCards);
        stmt.setString(4, yellowCards);
        stmt.setString(5, playerScored);
        stmt.setString(6, opponentName);

        // Updates the database
        stmt.executeUpdate();
    }
}