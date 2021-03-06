package Calendar.Controller;

import Match.Match;
import SQL.SqlConnection;
import Season.Season;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EditActivityMatchController {

    private static Match selectedMatch;
    private static Season selectedSeason;
    private ObservableList<Season> seasonData = FXCollections.observableArrayList();

    @FXML private ChoiceBox seasonChoiceBox;
    @FXML private TextField opponentInput;
    @FXML private DatePicker dateInput;
    @FXML private TextField timeInput;
    @FXML private CheckBox locationInput;
    @FXML private TextArea notesInput;
    @FXML private Button editButton;

    // Converts seasonData to Season name to be displayed in the Choicebox
    StringConverter<Season> seasonConverter = new StringConverter<>() {
        @Override public String toString(Season season) {
            return season.getName();
        }
        @Override public Season fromString(String id) {
            return null;
        }
    };

    @FXML public void initialize() {

        seasonChoiceBox.setConverter(seasonConverter);

        opponentInput.setText(selectedMatch.getOpponent());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        LocalDate selectedDate = LocalDate.parse(selectedMatch.getDate(), formatter);
        dateInput.setValue(selectedDate);
        dateInput.getEditor().setEditable(false);

        timeInput.setText(selectedMatch.getTime());
        locationInput.setSelected(selectedMatch.getIsHome());
        notesInput.setText(selectedMatch.getNote());

        loadDataFromDB();
    }

    public void initData(Match match){
        selectedMatch = match;
    }

    public void loadDataFromDB() {

        Connection conn = SqlConnection.connectToDB();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM seasons");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                seasonData.add(new Season(rs.getString("name"),
                        rs.getInt("season_id")));
            }

            seasonChoiceBox.setItems(seasonData);
            seasonData.stream().filter(season -> season.getId() == selectedMatch.getSeason());
            seasonChoiceBox.getSelectionModel().select(seasonData.get(0));
        } catch (SQLException e) {
            System.out.println("Could not retrieve data from seasons table " + e.getMessage());
        }
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

    public void editButtonClick(){
        String sql = "UPDATE matches SET opponent=?, season_id=?, date=?, time=?, isHome=?, note=? WHERE match_id = ?";
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, opponentInput.getText());
            stmt.setInt(2, ((Season) seasonChoiceBox.getSelectionModel().getSelectedItem()).getId());
            stmt.setString(3, dateInput.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            stmt.setString(4, timeInput.getText());
            stmt.setBoolean(5, locationInput.isSelected());
            stmt.setString(6, notesInput.getText());
            stmt.setInt(7, selectedMatch.getId());

            stmt.executeUpdate();

            SqlConnection.closeConnection();

            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}