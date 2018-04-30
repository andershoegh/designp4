package Lineup;

import Player.Player;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LineupController2 {

    private ObservableList<Player> playerData;
    @FXML
    private TableView<Player> playersTable;
    @FXML
    private TableColumn<?, ?> columnPlayers;

    @FXML
    public void initialize() {
        playerData = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDB();
    }

    private void setCellTable() {
        columnPlayers.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadDataFromDB() {
        Connection conn = SqlConnection.connectToDB();

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT player_id, name FROM players");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                playerData.add(new Player(rs.getInt("player_id"), rs.getString("name")));
            }

            playersTable.setItems(playerData);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SqlConnection.closeConnection();
    }

    private void dragAndDrop(ImageView image, GridPane gridPane) {
        List<Node> squares = gridPane.getChildren();
        for (int i = 0; i < 77; i++) {
            Node target = squares.get(i);
            image.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    Dragboard db = image.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(image.getImage());
                    db.setContent(content);
                    System.out.println("Drag detected");
                    event.consume();
                }
            });
            target.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.ANY);
                    }
                    int col = GridPane.getColumnIndex(target);
                    int row = GridPane.getRowIndex(target);
                    System.out.println("Drag Over Detected over column " + col + " and row " + row);
                    event.consume();
                }
            });
            target.setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    event.acceptTransferModes(TransferMode.ANY);
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasImage()) {
                        ImageView piece = new ImageView(db.getImage());
                        dragAndDrop(piece, gridPane);
                        gridPane.add(piece, GridPane.getColumnIndex(target), GridPane.getRowIndex(target));
                        success = true;
                    }
                    event.setDropCompleted(success);
                    System.out.println("Drop detected");
                    event.consume();
                }
            });
            image.setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        image.setImage(null);
                    }
                    System.out.println("Drag Complete!");
                    event.consume();
                }
            });
        }
    }
}
