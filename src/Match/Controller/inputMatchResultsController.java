package Match.Controller;

import Controller.MenuController;
import Match.Match;
import Player.Player;
import SQL.SqlConnection;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class inputMatchResultsController {

    private Match selectedMatch;
    private int i;
    private int inputGoalsScored = 0;
    private int matchId;
    private int goalsFor;
    private int goalsAgainst;
    private static String tempTable;
    private String teamName;
    private static Player tempPlayer;
    private static int tempAmount;
    private Player selectedItem;
    private Player temp;
    private Date lastClickTime;
    private ObservableList<Player> availablePlayers = FXCollections.observableArrayList();
    private ObservableList<Player> playerGoals = FXCollections.observableArrayList();
    private ObservableList<Player> playerAssists = FXCollections.observableArrayList();
    private ObservableList<Player> playerYellow = FXCollections.observableArrayList();
    private ObservableList<Player> playerRed = FXCollections.observableArrayList();
    private ObservableList<Player> updatedPlayers = FXCollections.observableArrayList();

    MenuController controller = new MenuController();
    MissingDataPopup controllerPopUp = new MissingDataPopup();

    @FXML private Label menuTeamName;
    @FXML private Label matchLabel;
    @FXML private Label dateLabel;
    @FXML private ListView listPlayers;

    @FXML private Label labelHomeTeam;
    @FXML private TextField textFieldHomeScore;

    @FXML private Label labelGuestTeam;
    @FXML private TextField textFieldGuestScore;

    @FXML private ChoiceBox<Player> choiceboxMOTM;
    @FXML private TextArea textAreaNote;
    @FXML private Label labelCharacters;

    @FXML private TableView<Player> tableGoals;
    @FXML private TableColumn<?, ?> columnGoalsName;
    @FXML private TableColumn<?, ?> columnGoalsAmount;

    @FXML private TableView<Player> tableAssists;
    @FXML private TableColumn<?, ?> columnAssistsName;
    @FXML private TableColumn<?, ?> columnAssistsAmount;

    @FXML private TableView<Player> tableYellow;
    @FXML private TableColumn<?, ?> columnYellowName;
    @FXML private TableColumn<?, ?> columnYellowAmount;

    @FXML private TableView<Player> tableRed;
    @FXML private TableColumn<?, ?> columnRedName;

    StringConverter<Player> converter = new StringConverter<Player>() {
        @Override public String toString(Player object) { return object.getName(); }
        @Override public Player fromString(String string) { return null; }
    };

    @FXML public void initialize(){
        setCellTable();
        choiceboxMOTM.setConverter(converter);
        loadDataFromDB();

        listPlayers.setCellFactory(new Callback<ListView, ListCell>() {
            @Override public ListCell call(ListView param) {
                ListCell<Player> cell = new ListCell<>() {
                    @Override protected void updateItem(Player p, boolean b) {
                        super.updateItem(p, b);
                        if (p != null) { setText(p.getName()); }
                    }
                };

                return cell;
            }
        });

        tableGoals.setItems(playerGoals);
        tableAssists.setItems(playerAssists);
        tableYellow.setItems(playerYellow);
        tableRed.setItems(playerRed);

        labelCharacters.setText(textAreaNote.getText().length() + " / 255");
        textAreaNote.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                labelCharacters.setText(newValue.length() + " / 255");

                if (newValue.length() > 255) {
                    labelCharacters.setTextFill(Color.valueOf("#ec2d28"));
                    textAreaNote.deleteNextChar();
                } else if (newValue.length() == 255) {
                    labelCharacters.setTextFill(Color.valueOf("#ec2d28"));
                } else {
                    labelCharacters.setTextFill(Color.valueOf("#c8c8c8"));
                }
            }
        });

        tableGoals.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        deleteRow("Goals");
                    }
                }
            }
        });

        tableAssists.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        deleteRow("Assists");
                    }
                }
            }
        });

        tableYellow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        deleteRow("Yellow");
                    }
                }
            }
        });

        tableRed.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        deleteRow("Red");
                    }
                }
            }
        });

        tableGoals
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        tableAssists.getSelectionModel().clearSelection();
                        tableYellow.getSelectionModel().clearSelection();
                        tableRed.getSelectionModel().clearSelection();
                    }
                });

        tableAssists
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        tableGoals.getSelectionModel().clearSelection();
                        tableYellow.getSelectionModel().clearSelection();
                        tableRed.getSelectionModel().clearSelection();
                    }
                });

        tableYellow
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        tableGoals.getSelectionModel().clearSelection();
                        tableAssists.getSelectionModel().clearSelection();
                        tableRed.getSelectionModel().clearSelection();
                    }
                });

        tableRed
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        tableGoals.getSelectionModel().clearSelection();
                        tableAssists.getSelectionModel().clearSelection();
                        tableYellow.getSelectionModel().clearSelection();
                    }
                });
    }

    public void setCellTable(){
        columnGoalsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnGoalsAmount.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));

        columnAssistsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAssistsAmount.setCellValueFactory(new PropertyValueFactory<>("assists"));

        columnYellowName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnYellowAmount.setCellValueFactory(new PropertyValueFactory<>("yellowCards"));

        columnRedName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void initData(Match match){
        selectedMatch = match;

        matchId = selectedMatch.getId();

        if(selectedMatch.getIsHome()) {
            matchLabel.setText(teamName + " vs " + selectedMatch.getOpponent());
            labelHomeTeam.setText(teamName);
            labelGuestTeam.setText(selectedMatch.getOpponent());
        } else {
            matchLabel.setText(selectedMatch.getOpponent() + " vs " + teamName);
            labelHomeTeam.setText(selectedMatch.getOpponent());
            labelGuestTeam.setText(teamName);
        }

        dateLabel.setText(selectedMatch.getDate());

        tableGoals.setItems(playerGoals);
        tableAssists.setItems(playerAssists);
        tableYellow.setItems(playerYellow);
        tableRed.setItems(playerRed);
    }

    public void loadDataFromDB(){
        Connection conn = SqlConnection.connectToDB();

        String sqlQuery = "SELECT *, players.name, players.goalScored, players.assist, players.yellowCards, players.redCards " +
                "FROM match_tactic_player INNER JOIN players ON match_tactic_player.player_id = players.player_id " +
                "WHERE match_tactic_player.match_id = ?";

        try {
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, matchId);

        ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                availablePlayers.addAll(new Player(rs.getInt("player_id"),
                        rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listPlayers.setItems(availablePlayers);
        choiceboxMOTM.setItems(availablePlayers);

        teamName = SqlConnection.getTeamNameFromDB();
        menuTeamName.setText(teamName);

        SqlConnection.closeConnection();
    }

    public void storeSelectedEntry(String table, Player player, int amount){
        tempTable = table;
        tempPlayer = player;
        tempAmount = amount;
    }

    public void storeSelectedEntry(String table, Player player){
        tempTable = table;
        tempPlayer = player;
    }

    public void updateObsList(String table, Player player, int amount){
        switch (table){
            case "Goals":
                player.setGoalsScored(amount);
                playerGoals.add(player);
                break;
            case "Assists":
                player.setAssists(amount);
                playerAssists.add(player);
                break;
            case "Yellow":
                player.setYellowCards(amount);
                playerYellow.add(player);
                break;
            case "Red":
                player.setRedCards(+1);
                playerRed.add(player);
                break;
        }
        tempTable = null;
        tempPlayer = null;
        tempAmount = 0;
    }

    public void addButtonClick(ActionEvent event){
        String table = ((Hyperlink) event.getSource()).getId();

        addResultEntryController addResultController = new addResultEntryController();
        switch (table){
            case "Goals":
                addResultController.initData(availablePlayers.filtered(player -> !playerGoals.contains(player)), table);
                break;
            case "Assists":
                addResultController.initData(availablePlayers.filtered(player -> !playerAssists.contains(player)), table);
                break;
            case "Yellow":
                addResultController.initData(availablePlayers.filtered(player -> !playerYellow.contains(player)), table);
                break;
            case "Red":
                addResultController.initData(availablePlayers.filtered(player -> !playerRed.contains(player)), table);
                break;
            default:
                break;
        }

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../addResultEntry.fxml"));
            Parent addResultFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            Scene addResultScene = new Scene(addResultFXML);
            stage.setScene(addResultScene);
            stage.showAndWait();

            if (tempPlayer != null) { updateObsList(tempTable, tempPlayer, tempAmount); }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRow(String table){
        switch (table) {
            case "Goals":
                playerGoals.remove(selectedItem);
                selectedItem.setGoalsScored(0);
                break;
            case "Assists":
                playerAssists.remove(selectedItem);
                selectedItem.setAssists(0);
                break;
            case "Yellow":
                playerYellow.remove(selectedItem);
                selectedItem.setYellowCards(0);
                break;
            case "Red":
                playerRed.remove(selectedItem);
                selectedItem.setRedCards(0);
                break;
        }
    }


    public void saveButtonClick(ActionEvent event){

        // Checks if score is input
        if (textFieldHomeScore.getText().isEmpty() || textFieldGuestScore.getText().isEmpty()) {
            controllerPopUp.initData("missing score");
            controller.popupSceneChange("../Match/missingDataPopup.fxml");
        } else {
            if (selectedMatch.getIsHome()) {
                goalsFor = Integer.parseInt(textFieldHomeScore.getText());
                goalsAgainst = Integer.parseInt(textFieldGuestScore.getText());
            } else {
                goalsAgainst = Integer.parseInt(textFieldHomeScore.getText());
                goalsFor = Integer.parseInt(textFieldGuestScore.getText());
            }

            playerGoals.stream().forEach(player -> inputGoalsScored = inputGoalsScored + player.getGoalsScored());

            if (goalsFor != inputGoalsScored){
                controllerPopUp.initData("missing goalscorer");
                controller.popupSceneChange("../Match/missingDataPopup.fxml");
            }
        }

        if (controllerPopUp.getCloseWindow()) {

            Connection conn = SqlConnection.connectToDB();

            // Inserting data into matches table
            try {
                String sqlMatch = "UPDATE matches SET goalsFor = ?, goalsAgainst = ?, note = ? WHERE match_id = ?";

                PreparedStatement stmt = conn.prepareStatement(sqlMatch);

                stmt.setInt(1, goalsFor);
                stmt.setInt(2, goalsAgainst);
                stmt.setString(3, textAreaNote.getText());
                stmt.setInt(4, matchId);

                // Updates the database
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Inserting data into players table
            // Combines separate table lists into one list
            updatedPlayers.addAll(tableGoals.getItems().filtered(player -> !updatedPlayers.contains(player)));
            updatedPlayers.addAll(tableAssists.getItems().filtered(player -> !updatedPlayers.contains(player)));
            updatedPlayers.addAll(tableYellow.getItems().filtered(player -> !updatedPlayers.contains(player)));
            updatedPlayers.addAll(tableRed.getItems().filtered(player -> !updatedPlayers.contains(player)));

            // Checks whether motm-selected player is in the updated list, and updates value
            if (choiceboxMOTM.getValue() != null) {
                if (!updatedPlayers.contains(choiceboxMOTM.getValue())) {
                    choiceboxMOTM.getValue().setMotm(+1);
                    updatedPlayers.add(choiceboxMOTM.getValue());
                } else {
                    choiceboxMOTM.getValue().setMotm(+1);
                }
            }

            // Updates the database for each player in the updatedPlayers list
            for (i = 0; i < updatedPlayers.size(); i++) {
                try {
                    String sqlPlayer = "UPDATE players SET goalScored = goalScored + ?, " +
                            "assist = assist + ?, yellowCards = yellowCards + ?, " +
                            "redCards = redCards + ?, motm = motm + ?" +
                            "WHERE player_id = ?";

                    PreparedStatement stmt = conn.prepareStatement(sqlPlayer);

                    int f = updatedPlayers.get(i).getGoalsScored();
                    stmt.setInt(1, updatedPlayers.get(i).getGoalsScored());
                    stmt.setInt(2, updatedPlayers.get(i).getAssists());
                    stmt.setInt(3, updatedPlayers.get(i).getYellowCards());
                    stmt.setInt(4, updatedPlayers.get(i).getRedCards());
                    stmt.setInt(5, updatedPlayers.get(i).getMotm());
                    stmt.setInt(6, updatedPlayers.get(i).getId());

                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            SqlConnection.closeConnection();

            controller.sceneChange(event, "../Match/MatchOverview.fxml");
        }
    }

    public void cancelButtonClick(ActionEvent event){
        controller.sceneChange(event, "../Match/MatchOverview.fxml");
    }

    // Menu navigation
    public void menuButtonClick(ActionEvent event){ controller.menuNavigation(event); }
}