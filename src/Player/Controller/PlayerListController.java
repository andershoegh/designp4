package Player.Controller;

import Controller.MenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Player.Player;
import SQL.SqlConnection;
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
    @FXML private TableView<Player> tablePlayers;
    @FXML private TableColumn<?, ?> columnName;
    @FXML private TableColumn<?, ?> columnPosition;
    @FXML private TableColumn<?, ?> columnMotm;

    // Runs when FXML is loaded
    @FXML
    public void initialize(){
        playerData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
    }

    public void clearTable(){
        tablePlayers.getItems().clear();
    }

    // Retrieves data from appropriate player class constructor
    private void setCellTable(){
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        columnMotm.setCellValueFactory(new PropertyValueFactory<>("motm"));
    }

    private void loadDataFromDB(){
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM player");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                playerData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), "00/00/00", rs.getInt("health"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("_id"))
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
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setScene(addPlayerScene);
            stage.showAndWait();

            clearTable();
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editPlayerButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../EditPlayer-Pop-up.fxml"));
            Parent editPlayerFXML = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            EditPlayerController controller = loader.getController();
            controller.initData(tablePlayers.getSelectionModel().getSelectedItem());

            Scene editPlayerScene = new Scene(editPlayerFXML);
            stage.setScene(editPlayerScene);
            stage.showAndWait();

            clearTable();
            initialize();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deletePlayerButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../DeletePlayer.fxml"));
            Parent deletePlayerFXML = loader.load();

            Stage stage = new Stage();
            // Prevents user interaction with other windows while popup is open
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Slet spiller");

            // Passes selected player info to DeletePlayerController.java
            DeletePlayerController controller = loader.getController();
            controller.initData(tablePlayers.getSelectionModel().getSelectedItem());

            Scene deletePlayerScene = new Scene(deletePlayerFXML);
            stage.setScene(deletePlayerScene);
            stage.showAndWait();

            clearTable();
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
