package Match.Controller;

import Controller.MenuController;
import Match.PlayerPos;
import Match.Match;
import Player.Player;
import SQL.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LineupController implements Initializable {
    private Match selectedMatch;
    private ArrayList<PlayerPos> playerPosList;

    @FXML private ObservableList<Player> playerData;
    @FXML public TableView<Player> playersTable;
    @FXML public TableColumn<?, ?> columnPlayers;
    @FXML public GridPane gridPane;
    @FXML public Button saveLineup;
    @FXML public Label matchLabel;
    @FXML public Label dateLabel;
    @FXML public Label timeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final GridPane target = gridPane;
        //ImageView source = imageView();
        final TableView<Player> source = playersTable;

        playerPosList = new ArrayList<>();
        playerData = FXCollections.observableArrayList();
        setCellTable();
        loadPlayerFromDB();
        updateTable();

        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Player selected = source.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    String id = Integer.toString(selected.getId());
                    content.putString(id);
                    //content.putString(selected.getName());
                    db.setContent(content);
                    //System.out.println(selected.getName());
                    event.consume();
                }
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

                    Player player = playerData.stream().filter(p -> p.getId() == Integer.parseInt(db.getString())).findFirst().get();
                    int playerID = Integer.parseInt(db.getString());

                    Text text = new Text(player.getName());
                    text.setFill(Color.WHITE);
                    ImageView image = new ImageView("file:graphics/player.png");
                    image.setPreserveRatio(true);
                    image.setFitWidth(30);
                    image.setFitHeight(30);

                    gridPane.add(text, x, y, 1, 1);
                    gridPane.add(image, x, y,1,1);
                    GridPane.setHalignment(text, HPos.CENTER);
                    GridPane.setValignment(text, VPos.BOTTOM);
                    GridPane.setValignment(image, VPos.TOP);
                    GridPane.setHalignment(image, HPos.CENTER);

                    PlayerPos playerPos =new PlayerPos(player.getName(),playerID, x, y);
                    playerPosList.add(playerPos);
                    playerData.remove(player);
                    updateTable();

                    image.setOnMouseClicked(e-> {
                        gridPane.getChildren().remove(text);
                        gridPane.getChildren().remove(image);
                        playerPosList.remove(playerPos);
                        playerData.add(player);
                        updateTable();
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
    }

    public void initData(Match match){
        selectedMatch = match;

        if(selectedMatch.getIsHome()) {
                matchLabel.setText("AAIF vs " + selectedMatch.getOpponent());
        } else {
            matchLabel.setText(selectedMatch.getOpponent() + " vs AAIF");
        }
        dateLabel.setText(selectedMatch.getDate());
        timeLabel.setText(selectedMatch.getTime());

        loadDataFromDB();
        displaySavedLineup();
    }

    public void displaySavedLineup(){
        if(!(playerPosList.isEmpty())) {
            for (PlayerPos playerPos : playerPosList) {
                Text text = new Text(playerPos.getPlayerName());
                text.setFill(Color.WHITE);
                ImageView image = new ImageView("file:graphics/player.png");
                image.setPreserveRatio(true);
                image.setFitWidth(30);
                image.setFitHeight(30);

                gridPane.add(text, playerPos.getxPos(), playerPos.getyPos(), 1, 1);
                gridPane.add(image, playerPos.getxPos(), playerPos.getyPos(), 1, 1);
                GridPane.setHalignment(text, HPos.CENTER);
                GridPane.setValignment(text, VPos.BOTTOM);
                GridPane.setValignment(image, VPos.TOP);
                GridPane.setHalignment(image, HPos.CENTER);

                Player player = playerData.stream().filter(p -> p.getId() == playerPos.getPlayerID()).findFirst().get();
                playerData.remove(player);
                updateTable();

                image.setOnMouseClicked(e-> {
                    gridPane.getChildren().remove(text);
                    gridPane.getChildren().remove(image);
                    playerPosList.remove(playerPos);
                    playerData.add(player);
                    updateTable();
                });
            }
        }
        else{
            return;
        }
    }

    public void saveLineupClick(){
            removeDataFromDB();
            String sql = "INSERT INTO match_tactic_player " +
                    "(player_id, match_id, pos_x, pos_y)" +
                    "VALUES (?, ?,? , ?)";
            try {
                Connection conn = SqlConnection.connectToDB();

                for (PlayerPos player : playerPosList) {
                    PreparedStatement stmt = conn.prepareStatement(sql);

                    stmt.setInt(1, player.getPlayerID());

                    stmt.setInt(2, selectedMatch.getId());

                    stmt.setInt(3, player.getxPos());

                    stmt.setInt(4, player.getyPos());

                    stmt.executeUpdate();
                }
                SqlConnection.closeConnection();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        try {
            Parent saveLineupFXML = FXMLLoader.load(getClass().getResource("../saveLineup-pop.fxml"));
            Scene saveLineupScene = new Scene(saveLineupFXML);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Taktik gemt!");
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            stage.setScene(saveLineupScene);
            stage.showAndWait();
            stage.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void removeDataFromDB() {
        Connection conn = SqlConnection.connectToDB();
        String sql = "DELETE FROM match_tactic_player WHERE match_id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, selectedMatch.getId());
            stmt.executeUpdate();
            }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            SqlConnection.closeConnection();
        }
    }

    public void loadDataFromDB(){
        Connection conn = SqlConnection.connectToDB();
        String sqlQuery = "SELECT players.name, players.player_id, match_tactic_player.pos_x, match_tactic_player.pos_y " +
                "FROM match_tactic_player INNER JOIN players " +
                "ON match_tactic_player.player_id = players.player_id WHERE match_tactic_player.match_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setInt(1, selectedMatch.getId());

            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return;
            }

            while(rs.next()){
                playerPosList.add(new PlayerPos(rs.getString("name"),
                                            rs.getInt("player_id"),
                                            rs.getInt("pos_x"),
                                            rs.getInt("pos_y")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SqlConnection.closeConnection();
    }

    private void setCellTable() {
        columnPlayers.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadPlayerFromDB() {
        Connection conn = SqlConnection.connectToDB();

        try {
            PreparedStatement statement = conn.prepareStatement("SELECT player_id, name FROM players");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                playerData.add(new Player(rs.getInt("player_id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SqlConnection.closeConnection();
    }

    private void updateTable(){
        playersTable.setItems(playerData);
    }

    public void cancelButtonClick(ActionEvent event){
        controller.sceneChange(event, "../Match/MatchOverview.fxml");
    }

    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }


}
