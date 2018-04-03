package player.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.Player;
import sql.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerListController {

    private ObservableList<Player> playerData;

    // setting FXML IDs
    @FXML
    private TableView<Player> tablePlayers;
    @FXML
    private TableColumn<?, ?> columnName;
    @FXML
    private TableColumn<?, ?> columnPosition;

    // Runs when FXML is loaded
    @FXML
    public void initialize(){
        playerData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
    }

    // Retrieves data from appropriate player class constructor
    private void setCellTable(){
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
    }

    private void loadDataFromDB(){
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM player");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                playerData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), "00/00/00", rs.getString("health"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // inputting retrieved data from db into table row
        tablePlayers.setItems(playerData);

        SqlConnection.closeConnection();
    }

    public void addPlayerButtonClick(ActionEvent event){

        // Switching scene from PlayerList.FXML to AddPlayer.FXML
        try {
            Parent addPlayerFXML = FXMLLoader.load(getClass().getResource("../AddPlayer.fxml"));
            Scene addPlayerScene = new Scene(addPlayerFXML);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(addPlayerScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
