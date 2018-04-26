package Lineup;

import Player.Player;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LineupController3 implements Initializable {
    @FXML
    private ObservableList<Player> playerData;

    @FXML
    public TableView<Player> playersTable;
    @FXML
    public TableColumn<?, ?> columnPlayers;
    @FXML
    public Text[][] textfield;
    @FXML
    public GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textfield = new Text[7][11];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 11; j++) {
                textfield[i][j] = new Text();
                //textfield[i][j].setText("test");
                gridPane.add(textfield[i][j], i, j);
            }
        }

        final GridPane target = gridPane;
        //ImageView source = imageView();
        final TableView<Player> source = playersTable;

        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Player selected = source.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(selected.getName());
                    db.setContent(content);
                    System.out.println(selected.getName());
                    event.consume();
                }
            }
        });

        target.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Drag detected");
                Dragboard db = target.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(target.toString());
                db.setContent(content);
                event.consume();
            }
        });

        target.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                    System.out.println("Drag entered");
                }
                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                Node node = event.getPickResult().getIntersectedNode();
                if (node != target && db.hasString()) {
                    Integer cIndex = GridPane.getColumnIndex(node);
                    Integer rIndex = GridPane.getRowIndex(node);
                    int x = cIndex == null ? 0 : cIndex;
                    int y = rIndex == null ? 0 : rIndex;
                    Text text = new Text(db.getString());
//                    text.setTextAlignment(TextAlignment.CENTER);
                    ImageView image = new ImageView("file:../../graphics/player.png");
                    image.setPreserveRatio(true);
                    image.setFitWidth(30);
                    image.setFitHeight(30);

                    gridPane.add(text, x, y, 1, 1);
                    gridPane.add(image,x,y,1,1);
                    success = true;
                    System.out.println("Drop Successful!! to col: " + x + " and row: " + y);
                }
                event.setDropCompleted(success);

                event.consume();
            }
        });

        target.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            System.out.println(String.valueOf(e.getSceneX()));
            System.out.println(String.valueOf(e.getSceneY()));

        });
        target.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("Drag Exited");
                event.consume();
            }
        });

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
}
