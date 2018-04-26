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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

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
    @FXML private ImageView imageView;
    @FXML private GridPane gridPane;
    @FXML private Text text3;

    @FXML public void initialize(){
        playerData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
/*
        for(int i = 0; i<7; i++) {
            for(int j = 0; j<11; j++) {
                gridPane.add(new ImageView());
            }
        }
*/
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
    private void handleDragDetection (MouseEvent event) {

        System.out.println("dragdetected");
        Player selected = playersTable.getSelectionModel().getSelectedItem();
        if(selected != null) {
            Dragboard db = playersTable.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(selected.getName());
            db.setContent(content);
            System.out.println(selected.getName());
            event.consume();
        }
        /*
        Dragboard db = iv24.startDragAndDrop(TransferMode.ANY);

        ClipboardContent cb = new ClipboardContent();
        cb.putImage(iv24.getImage());

        db.setContent(cb);

        event.consume(); */
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        System.out.println("dragover detected");
        Dragboard db = event.getDragboard();
        if(event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    private void handleDragDropped(DragEvent event) {
        System.out.println("dragdropped detected");
        Dragboard db = event.getDragboard();
        boolean success = false;
        if(event.getDragboard().hasString()) {
            //String text1 = db.getString();
            text3.setText(db.getString());

            playersTable.setItems(playerData);
            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
        /*
        List<File> files = event.getDragboard().getFiles();
        Image img = new Image(new FileInputStream(files.get(0)));
        iv24.setImage(img); */
    }

    @FXML
    private void handleMouseClicked (MouseEvent event) {
        System.out.println("selection detected");
        Player selected = playersTable.getSelectionModel().getSelectedItem();
        if(selected != null) {
            System.out.println("select : " +selected.getName());
        }
    }
}