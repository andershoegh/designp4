package Calendar.Controller;

import SQL.SqlConnection;
import Season.Season;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class CreateActivityMatchController {

    private ObservableList<Season> seasonData = FXCollections.observableArrayList();

    @FXML private TextField opponentInput;
    @FXML private DatePicker dateInput;
    @FXML private TextField timeInput;
    @FXML private CheckBox locationInput;
    @FXML private TextArea notesInput;
    @FXML private Button createButton;
    @FXML private ChoiceBox<Season> seasonChoiceBox;


    // Converts seasonData to Season name to be displayed in the Choicebox
    StringConverter<Season> converter = new StringConverter<>() {
        @Override public String toString(Season season) {
            return season.getName();
        }
        @Override public Season fromString(String id) {
            return null;
        }
    };

    @FXML public void initialize(){

        loadDataFromDB();

        seasonChoiceBox.setConverter(converter);
    }


    public void loadDataFromDB(){
        Connection conn = SqlConnection.connectToDB();

        try {
            PreparedStatement seasonStatement = conn.prepareStatement("SELECT * FROM seasons");
            ResultSet rsSeason = seasonStatement.executeQuery();

            while (rsSeason.next()){ seasonData.addAll(new Season(rsSeason.getString("name"), rsSeason.getInt("season_id"))); }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        seasonChoiceBox.setItems(seasonData);

        SqlConnection.closeConnection();
    }

    public void createSeasonButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Match/newSeasonPop.fxml"));
            Parent createEventFXML = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene createEventScene = new Scene(createEventFXML);
            stage.setScene(createEventScene);
            stage.showAndWait();
            initialize();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createButtonClick() {
        String sql = "INSERT INTO matches(match_id, opponent, goalsFor, goalsAgainst, season_id, date, time, isHome, note)" +
                "VALUES (null, ?, -1, -1, ?, ?, ?, ?, ?)";
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, opponentInput.getText() + " ");

            //Sets the season_id as foreign key
            stmt.setInt(2, seasonChoiceBox.getValue().getId());

            // Passes date input to DB
            String date = dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            stmt.setString(3, date);

            // Passes time input to DB
            stmt.setString(4, timeInput.getText());

            // Passes location input to DB
            stmt.setInt(5, locationInput.isSelected() ? 1 : 0);

            // Passes notes input to DB
            stmt.setString(6, notesInput.getText());

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void backButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../createActivityMenu.fxml"));
            Parent createEventFXML = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            Scene createEventScene = new Scene(createEventFXML);
            stage.setScene(createEventScene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

