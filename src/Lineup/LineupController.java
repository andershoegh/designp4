package Lineup;

import Player.Player;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LineupController {

    private ObservableList<Player> playerData;

    @FXML private TableView<Player> playersTable;
    @FXML private TableColumn<?, ?> columnPlayers;
    @FXML private ImageView iv24;

    @FXML public void initialize(){
        playerData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
    }

    private void setCellTable(){
        columnPlayers.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadDataFromDB(){
        Connection conn = SqlConnection.connectToDB();

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT player_id, name FROM players");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                playerData.add(new Player(rs.getInt("player_id"), rs.getString("name")));
            }

            playersTable.setItems(playerData);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SqlConnection.closeConnection();
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        if(event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void handleDrop(DragEvent event) throws FileNotFoundException {
        List<File> files = event.getDragboard().getFiles();
        Image img = new Image(new FileInputStream(files.get(0)));
        iv24.setImage(img);
    }

    @FXML
    private void handleDragDetection (DragEvent event) {
        Dragboard db = iv24.startDragAndDrop(TransferMode.ANY);

        ClipboardContent cb = new ClipboardContent();
        cb.putImage(iv24.getImage());

        db.setContent(cb);

        event.consume();
    }
}