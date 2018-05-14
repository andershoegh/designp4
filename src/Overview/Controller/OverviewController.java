package Overview.Controller;

import Controller.MenuController;
import Match.Match;
import Player.Player;
import SQL.SqlConnection;
import Team.Team;
import Training.Training;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;

public class OverviewController {

    MenuController controller = new MenuController();
    private ObservableList<Training> trainingList = FXCollections.observableArrayList();
    private ObservableList<Training> filteredTrainingList = FXCollections.observableArrayList();
    private ObservableList<Match> matchList = FXCollections.observableArrayList();
    private ObservableList<Match> filteredMatchList = FXCollections.observableArrayList();
    private ObservableList<Player> playerList = FXCollections.observableArrayList();
    private ObservableList<Player> filteredPlayerList = FXCollections.observableArrayList();
    private ObservableList<String> attendingPlayers = FXCollections.observableArrayList();
    private ObservableList<Team> teamData;

    private Date date;
    private DropShadow shadow;

    @FXML private VBox trainingColumn;
    @FXML private VBox matchColumn;
    @FXML private VBox birthdayColumn;
    @FXML private Label teamName;
    @FXML private javafx.scene.image.ImageView teamPic;

    @FXML
    public void initialize() {
        date = new Date();

        shadow = new DropShadow();
        shadow.setWidth(30.0);
        shadow.setHeight(30.0);
        shadow.setRadius(14.5);
        shadow.setOffsetX(4.0);
        shadow.setOffsetY(4.0);
        shadow.setColor(Color.rgb(171, 168, 168));

        teamData = FXCollections.observableArrayList();

        loadDataFromDB();
        loadDataFromDBLabel();
        checkTeamDBLabel();
    }

    public void loadDataFromDB() {
        Connection conn = SqlConnection.connectToDB();

        try {
            // Loading data from training
            PreparedStatement trainingStatement = conn.prepareStatement("SELECT * FROM trainings");
            ResultSet rsTraining = trainingStatement.executeQuery();

            while(rsTraining.next()) {
                trainingList.add(new Training(rsTraining.getInt("training_id"),
                        rsTraining.getString("date"),
                        rsTraining.getString("start_time"),
                        rsTraining.getString("end_time"),
                        rsTraining.getInt("program_id")));
            }

            // Filtering and sorting the list to only contain trainings after the current date
            filteredTrainingList = trainingList.filtered(training -> training.getConvertedDate().after(date)).sorted(trainingComparator);

            // creating 0, 1 or 2 boxes displaying training data, depending on the number of trainings in the filtered list
            if (filteredTrainingList.size() > 0) {
                getAttendingPlayers(filteredTrainingList.get(0), conn);
                createTrainingBox(filteredTrainingList.get(0));

                if(filteredTrainingList.size() > 1) {
                    getAttendingPlayers(filteredTrainingList.get(1), conn);
                    createTrainingBox(filteredTrainingList.get(1));
                }
            } else {
               createMissingMessage("Der er ikke nogen kommende træninger", trainingColumn);
            }

            // Matches
            PreparedStatement matchStatement = conn.prepareStatement("SELECT * FROM matches");
            ResultSet rsMatch = matchStatement.executeQuery();

            while (rsMatch.next()) {
                matchList.add(new Match(rsMatch.getString("opponent"),
                        rsMatch.getInt("goalsFor"),
                        rsMatch.getInt("goalsAgainst"),
                        rsMatch.getInt("season_id"),
                        rsMatch.getString("date"),
                        rsMatch.getString("time"),
                        rsMatch.getInt("match_id"), 0,
                        rsMatch.getBoolean("isHome")));
            }

            filteredMatchList = matchList.filtered(match -> match.getConvertedDate().after(date)).sorted(matchComparator);

            if (filteredMatchList.size() > 0) {
                createMatchBox(filteredMatchList.get(0), getAttendingPlayers(filteredMatchList.get(0), conn));
            } else {
                createMissingMessage("Der er ikke nogen kommenede kampe", matchColumn);
            }

            // Birthday
            PreparedStatement playerStatement = conn.prepareStatement("SELECT * FROM players");
            ResultSet rsPlayer = playerStatement.executeQuery();

            while (rsPlayer.next()) {
                playerList.add(new Player(rsPlayer.getInt("player_id"),
                        rsPlayer.getString("name"),
                        rsPlayer.getString("birthday")));
            }

            filteredPlayerList = playerList
                    .filtered(player -> player.getConvertedDate().getMonth() > date.getMonth())
                    .filtered(player -> player.getConvertedDate().getDate() > date.getDate())
                    .sorted(playerComparator);

            System.out.println(playerList);
            System.out.println(filteredPlayerList.size());

            if (filteredPlayerList.size() > 0) {
                createBirthdayBox(filteredPlayerList.get(0));
            } else {
                createMissingMessage("Der er ikke nogen kommende fødselsdage", birthdayColumn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SqlConnection.closeConnection();
    }

    public void getAttendingPlayers(Training training, Connection conn) {

        try {
            String query = "SELECT * FROM training_players WHERE training_id = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, training.getId());

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                training.setAttending(training.getAttending() + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getAttendingPlayers(Match match, Connection conn) {

        try {
            String query = "SELECT *, players.name FROM match_tactic_player INNER JOIN players " +
                    "ON match_tactic_player.player_id = players.player_id WHERE match_tactic_player.match_id = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, match.getId());

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                attendingPlayers.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attendingPlayers;
    }

    public void createMissingMessage(String message, VBox box) {
        Label text = new Label(message);
        text.setStyle("-fx-text-fill: #9c9c9c");
        box.getChildren().add(text);
    }

    public void createTrainingBox(Training training){

        VBox vBox = new VBox();
        vBox.setPrefWidth(324.0);
        vBox.setPrefHeight(180.0);
        vBox.setStyle("-fx-background-color: white");
        vBox.setEffect(shadow);
        vBox.alignmentProperty().setValue(Pos.TOP_CENTER);

        GridPane grid = new GridPane();
        grid.setMinWidth(324.0);
        grid.setMinHeight(90.0);
        grid.setPadding(new Insets(0, 0, 0, 30));

        Label dateLabel = new Label("Dato:");
        dateLabel.getStyleClass().add("overviewText");
        Label date = new Label(training.getConvertedDate().getDate() + " / " + (training.getConvertedDate().getMonth() + 1));
        Label timeLabel = new Label("Tidspunkt:");
        timeLabel.getStyleClass().add("overviewText");
        Label time = new Label(training.getStartTime() + " - " + training.getEndTime());
        Label attendingLabel = new Label("Antal tilmeldte:");
        attendingLabel.getStyleClass().add("overviewText");
        Label attending = new Label(Integer.toString(training.getAttending()));

        Button trainingProgramButton = new Button("Træningsprogram");
        trainingProgramButton.getStyleClass().add("overviewButtons");
        trainingProgramButton.setMinWidth(135.0);

        vBox.getChildren().addAll(grid, trainingProgramButton);
        grid.setHgap(30.0);
        grid.setVgap(12.0);
        grid.addRow(1, dateLabel, date);
        grid.addRow(2, timeLabel, time);
        grid.addRow(3, attendingLabel, attending);
        vBox.setMargin(grid, new Insets(15, 0, 0, 0));
        vBox.setMargin(trainingProgramButton, new Insets(15, 0, 20, 0));

        trainingColumn.getChildren().add(vBox);
    }

    public void createMatchBox(Match match, ObservableList attendingPlayers) {

        VBox vBox = new VBox();
        vBox.setPrefWidth(324.0);
        vBox.setPrefHeight(286.0);
        vBox.setStyle("-fx-background-color: white");
        vBox.setEffect(shadow);
        vBox.alignmentProperty().setValue(Pos.TOP_CENTER);

        GridPane grid = new GridPane();
        grid.setMinWidth(324.0);
        grid.setMinHeight(60.0);
        grid.setPadding(new Insets(0, 0, 0, 30));

        Label dateLabel = new Label("Dato:");
        dateLabel.getStyleClass().add("overviewText");
        Label date = new Label(match.getConvertedDate().getDate() + " / " + (match.getConvertedDate().getMonth() + 1));
        Label opponentLabel = new Label("Modstander:");
        opponentLabel.getStyleClass().add("overviewText");
        Label opponent = new Label(match.getOpponent());
        Label placeLabel = new Label("Sted:");
        placeLabel.getStyleClass().add("overviewText");
        Label place = match.getIsHome() ? new Label("Hjemme") : new Label("Ude");
        Label attendingLabel = new Label("Tilmeldte:");
        attendingLabel.getStyleClass().add("overviewText");

        ListView<String> attendingListView = new ListView<>(attendingPlayers);
        attendingListView.setMaxHeight(108.0);
        attendingListView.setMaxWidth(180.0);

        Button tacticButton = new Button("Taktik");
        tacticButton.getStyleClass().add("overviewButton");
        tacticButton.setMinWidth(120.0);

        vBox.getChildren().addAll(grid, tacticButton);
        grid.setHgap(10.0);
        grid.setVgap(12.0);
        grid.addRow(1, dateLabel, date);
        grid.addRow(2, opponentLabel, opponent);
        grid.addRow(3, placeLabel, place);
        grid.addRow(4, attendingLabel, attendingListView);
        grid.setMargin(attendingListView, new Insets(0, 5, 0, 0));
        vBox.setMargin(grid, new Insets(15, 0, 0, 0));
        vBox.setMargin(tacticButton, new Insets(25, 0, 20, 0));

        matchColumn.getChildren().add(vBox);
    }

    public void createBirthdayBox(Player player) {

        VBox vBox = new VBox();
        vBox.setPrefWidth(324.0);
        vBox.setPrefHeight(90.0);
        vBox.setStyle("-fx-background-color: white");
        vBox.setEffect(shadow);
        vBox.alignmentProperty().setValue(Pos.TOP_LEFT);

        Label dateLabel = new Label(player.getConvertedDate().getDate() + " / " + player.getConvertedDate().getMonth());
        dateLabel.getStyleClass().add("overviewText");
        Label playerInfoLabel = new Label(player.getName() + ", " + (date.getYear() - player.getConvertedDate().getYear() + " år"));

        vBox.getChildren().addAll(dateLabel, playerInfoLabel);
        vBox.setMargin(dateLabel, new Insets(15, 0, 0, 0));
        vBox.setMargin(playerInfoLabel, new Insets(10, 0, 20, 0));

        birthdayColumn.getChildren().add(vBox);
    }
    public void loadDataFromDBLabel() {
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM team");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                teamData.add(new Team(
                        rs.getInt("team_id"),
                        rs.getString("team_name"),
                        rs.getString("team_image")));
            }
            SqlConnection.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkTeamDBLabel() {
        try {
            if (teamData.get(0).getTeamName().equals("noname")) {
                System.out.println("--- CoachDB does not have a team. 'addTeam' loaded ---");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../addTeam.fxml"));
                Parent createTeam = loader.load();

                Stage stage = new Stage();
                stage.setResizable(false);
                // Prevents user interaction with other windows while popup is open
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Opret hold");
                stage.getIcons().add(new Image("file:graphics/ball.png"));

                Scene createTeamForProgram = new Scene(createTeam);
                stage.setScene(createTeamForProgram);
                stage.setResizable(false);
                stage.isAlwaysOnTop();
                stage.showAndWait();
            } else {
                teamName.setText(teamData.get(0).getTeamName());
                Team teamName = new Team(1, teamData.get(0).getTeamName(), null);
                System.out.println(teamData.get(0).getTeamName() + " is the name of the team.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            
        } finally {
            teamData.remove(0);
            loadDataFromDBLabel();
            teamName.setText(teamData.get(0).getTeamName());
        }
    }

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }

    Comparator<Training> trainingComparator = Comparator.comparing(Training::getConvertedDate);

    Comparator<Match> matchComparator = Comparator.comparing(Match::getConvertedDate);

    Comparator<Player> playerComparator = (player1, player2) -> {
        if (player1.getConvertedDate().getMonth() < player2.getConvertedDate().getMonth()) {
            return -1;
        } else if (player1.getConvertedDate().getMonth() > player2.getConvertedDate().getMonth()) {
            return 1;
        } else {
            if (player1.getConvertedDate().getDate() < player2.getConvertedDate().getDate()) {
                return -1;
            } else if (player1.getConvertedDate().getDate() > player2.getConvertedDate().getDate()) {
                return 1;
            } else {
                return 0;
            }
        }
    };
}
