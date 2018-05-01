package Lineup;

import Controller.MenuController;
import Player.Player;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    @FXML
    public Button saveLineup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textfield = new Text[7][11];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 11; j++) {
                textfield[i][j] = new Text();
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

//        target.setOnDragDetected(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("Drag detected");
//                Dragboard db = target.startDragAndDrop(TransferMode.MOVE);
//                ClipboardContent content = new ClipboardContent();
//                content.putString(target.toString());
//                db.setContent(content);
//                event.consume();
//            }
//        });

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
                    ImageView image = new ImageView("file:../../graphics/player.png");
                    image.setPreserveRatio(true);
                    image.setFitWidth(30);
                    image.setFitHeight(30);

                    gridPane.add(text, x, y, 1, 1);
                    gridPane.add(image,x,y,1,1);
                    GridPane.setHalignment(text, HPos.CENTER);
                    GridPane.setValignment(text, VPos.BOTTOM);
                    GridPane.setValignment(image, VPos.TOP);
                    GridPane.setHalignment(image, HPos.CENTER);

                    image.setOnMouseClicked(e-> {
                        gridPane.getChildren().remove(text);
                        gridPane.getChildren().remove(image);
                    });

                    success = true;
                    System.out.println("Drop Successful!! to col: " + x + " and row: " + y);
                }
                event.setDropCompleted(success);

                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("Drag Exited");
                event.consume();
            }
        });

        saveLineup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ImageView imageview = new ImageView();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Gem opstilling");

                FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
                FileChooser.ExtensionFilter extensionFilter1 = new FileChooser.ExtensionFilter("All Files", "*.*");
                fileChooser.getExtensionFilters().add(extensionFilter);
                fileChooser.getExtensionFilters().add(extensionFilter1);

                File file = fileChooser.showSaveDialog(null);

                if (file != null) {
                    try {
                        Robot robot = new Robot();
//                        Rectangle rectangle = new Rectangle(100, 0, 1200, 1200);
                        java.awt.Rectangle rectangle = new java.awt.Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                        Thread.sleep(500);
                        BufferedImage image = robot.createScreenCapture(rectangle);

                        ImageIO.write(image, "png", file);
                    } catch (Exception ex) {
                        Logger.getLogger(LineupController3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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


    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }


}
