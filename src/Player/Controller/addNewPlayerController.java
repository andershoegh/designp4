package Player.Controller;

import SQL.SqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addNewPlayerController {

    @FXML
    private TextField nameInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField mailInput;
    @FXML
    private TextField ICEnameInput;
    @FXML
    private TextField ICEphoneInput;
    @FXML
    private TextField positionInput;
    @FXML
    private TextField birthdayInput;
    @FXML
    private TextField healthInput;

    public void acceptButtonClick(){

        Connection conn = SqlConnection.connectToDB();
        String sql = "INSERT INTO player "
                + " (_id, name, address, phone, mail," +
                "iceName, iceTelephone, position, health, " +
                "yellowCards, redCards, goalScored, assist, motm, " +
                "attendedMatches, attendedTrainings)" +

                " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, null, null, null, null, null, null, null)";

        // Connecting to the database
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Inserting the values into the database
            stmt.setString(1, nameInput.getText());
            stmt.setString(2, addressInput.getText());
            stmt.setInt(3, Integer.parseInt(phoneInput.getText()));
            stmt.setString(4, mailInput.getText());
            stmt.setString(5, ICEnameInput.getText());
            stmt.setInt(6, Integer.parseInt(ICEphoneInput.getText()));
            stmt.setString(7, positionInput.getText());
            stmt.setString(8, healthInput.getText());

            // Updates the database
            stmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        SqlConnection.closeConnection();
    }
}
