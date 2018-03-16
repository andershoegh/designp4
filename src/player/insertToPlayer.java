import java.sql.*;
import java.util.Scanner;

class insertToPlayer {

    public static void insertPlayers(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Navn: ");
        String name = scanner.nextLine();
        if(name.equals("")){
            name = null;
        }

        System.out.print("Addresse: ");
        String address = scanner.nextLine();
        if(address.equals("")){
            address = null;
        }

        System.out.print("Telefonnummer: ");
        String phone = scanner.nextLine();
        if(phone.equals("")){
            phone = null;
        }

        System.out.print("Email: ");
        String mail = scanner.nextLine();
        if(mail.equals("")){
            mail = null;
        }

        System.out.print("ICE (In Case of Emergency) navn: ");
        String iceName = scanner.nextLine();
        if(iceName.equals("")){
            iceName = null;
        }

        System.out.print("ICE (In Case of Emergency) telefonnummer: ");
        String iceTelephone = scanner.nextLine();
        if(iceTelephone.equals("")){
            iceTelephone = null;
        }

        System.out.print("Position: ");
        String position = scanner.nextLine();
        if(position.equals("")){
            position = null;
        }

        System.out.print("Er vedkommende skadet? (1= Nej, 2=Ja): ");
        String health = scanner.nextLine();
        if(health.equals("")){
            health = "1";
        }


        // Prepareing to insert the values into database
        String sql = "INSERT INTO player "
                + " (_id, name, address, phone, mail," +
                "iceName, iceTelephone, position, health, " +
                "yellowCards, redCards, goalScored, assist, motm, " +
                "attendedMatches, attendedTrainings)" +

                " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, null, null, null, null, null, null, null)";

        // Connecting to the database
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Inserting the values into the database
        stmt.setString(1, name);
        stmt.setString(2, address);
        stmt.setString(3, phone);
        stmt.setString(4, mail);
        stmt.setString(5, iceName);
        stmt.setString(6, iceTelephone);
        stmt.setString(7, position);
        stmt.setString(8, health);

        // Updates the database
        stmt.executeUpdate();
    }
}