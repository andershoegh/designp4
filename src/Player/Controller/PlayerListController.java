package Player.Controller;

import Controller.MenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Player.Player;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class PlayerListController {

    private ObservableList<Player> playerData;

    // setting FXML IDs
    @FXML private Label menuTeamName;
    @FXML private TableView<Player> tablePlayers;
    @FXML private TableColumn<?, ?> columnName;
    @FXML private TableColumn<?, ?> columnPosition;
    @FXML private TableColumn<?, ?> columnPhone;
    @FXML private TableColumn<?, ?> columnMail;
    @FXML private TableColumn<?, ?> columnAddress;
    @FXML private TableColumn<?, ?> columnBirthday;
    @FXML private Button show_player_btn;
    @FXML private Button deleteButton;

    // Runs when FXML is loaded
    @FXML public void initialize(){
        playerData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();

        tablePlayers
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        show_player_btn.setDisable(false);
                        deleteButton.setDisable(false);
                    }
                });

        show_player_btn.setDisable(true);
        deleteButton.setDisable(true);
    }

    // Checks for double clicks, and opens edit window accordingly.
    private Player temp;
    private Date lastClickTime;
    @FXML
    private void handleRowSelect() {
        Player row = tablePlayers.getSelectionModel().getSelectedItem();
        if (row == null)
            return;
        if(row != temp){
            temp = row;
            lastClickTime = new Date();
        } else if(row == temp) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300){ //another click registered in 300 millis
                System.out.println("--- Double Clicked on a row! Will open edit window. ---");
                editPlayerButtonClick();
            } else {
                lastClickTime = new Date();
            }
        }
    }

    public void clearTable(){
        tablePlayers.getItems().clear();
    }

    // Retrieves data from appropriate player class constructor
    private void setCellTable(){
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        columnPhone.setCellValueFactory(new PhonePropertyValueFactory<>("phone"));
        columnMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
    }

    private void loadDataFromDB() {
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM players ORDER BY name ASC");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                playerData.add(new Player(rs.getString("name"), rs.getString("address"), rs.getInt("phone"),
                        rs.getString("mail"), rs.getString("iceName"), rs.getInt("iceTelephone"),
                        rs.getString("position"), rs.getInt("health"), rs.getString("birthday"),
                        rs.getInt("yellowCards"), rs.getInt("redCards"), rs.getInt("goalScored"),
                        rs.getInt("assist"), rs.getInt("motm"), rs.getInt("attendedMatches"),
                        rs.getInt("attendedTrainings"), rs.getInt("player_id"))
                );
            }

            menuTeamName.setText(SqlConnection.getTeamNameFromDB());

            SqlConnection.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Inputs retrieved data from the database into rows
        tablePlayers.setItems(playerData);
    }

    public void addPlayerButtonClick(ActionEvent event){
        // Switching scene from PlayerList.FXML to AddPlayer.FXML
        try {
            Parent addPlayerFXML = FXMLLoader.load(getClass().getResource("../AddPlayer.fxml"));
            Scene addPlayerScene = new Scene(addPlayerFXML);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Opret spiller");
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            stage.setScene(addPlayerScene);
            stage.showAndWait();

            clearTable();
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editPlayerButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../EditPlayer-Pop-up.fxml"));
            Parent editPlayerFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Vis spiller");
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            EditPlayerController controller = loader.getController();
            controller.initData(tablePlayers.getSelectionModel().getSelectedItem());

            Scene editPlayerScene = new Scene(editPlayerFXML);
            stage.setScene(editPlayerScene);
            stage.showAndWait();

            clearTable();
            initialize();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayerButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../DeletePlayer.fxml"));
            Parent deletePlayerFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            // Prevents user interaction with other windows while popup is open
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Slet spiller");
            stage.getIcons().add(new Image("file:graphics/ball.png"));
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
