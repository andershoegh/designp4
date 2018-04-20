package Calendar.Controller;

import Controller.MenuController;
import Match.Match;
import SQL.SqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarController {

    private ObservableList<Match> matchList= FXCollections.observableArrayList();
    private ObservableList<?> trainings = FXCollections.observableArrayList();
    private ObservableList<?> others = FXCollections.observableArrayList();

    private ObservableList<String> StrMatchList = FXCollections.observableArrayList();
    private Date date;
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

    // setting FXML IDs
    @FXML private Label MonthYearLabel;
    @FXML private Button PrevMonthButton;
    @FXML private Button NextMonthButton;
    @FXML private TableView<?>OtherTableView;
    @FXML private TableView<?>matchTableView;
    @FXML private TableView<?>TrainingTableView;
    @FXML private Button OpretButton;

    //Running methods when scene gets loaded
    @FXML
    public void initialize() throws ParseException {
        date = new Date();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());
        loadMatchFromDB();
    }


    //Clear the table view
    public void clearMatchList(){matchTableView.getItems().clear();}



    //Loads data from Match table in DB
    private void loadMatchFromDB() throws ParseException {
        clearMatchList();
        matchList.clear();

        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM matches");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                matchList.add(new Match(rs.getString("opponent"), rs.getString("date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //looping though the matchList to add the relevant data to a string list (StrMatchList) for the viewList
        for(int i=0; i< matchList.size();i++){
            //Parsing the date-string-variable to a datatype of date named localDate
            DateFormat format = new SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH);
            Date localDate = format.parse(matchList.get(i).getDate());

            //Checks if the month of the match is equal to the month of the calender
            if(localDate.getMonth() == date.getMonth()){
                StrMatchList.add(matchList.get(i).getOpponent() + " " + matchList.get(i).getDate());
            }
        }

        // inputting retrieved data from db into list view
       // matchTableView.setItems(StrMatchList);
        SqlConnection.closeConnection();
    }


    //Sets next month in calender
    public void NextMonthButtonClick() throws ParseException {
        matchList.clear();
        clearMatchList();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        date = c.getTime();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());

        loadMatchFromDB();
    }

    //Sets prev month in calender
    public void PrevMonthButtonClick() throws ParseException {
        clearMatchList();
        matchList.clear();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        date = c.getTime();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());

        loadMatchFromDB();
    }


    //Loader Create event pop-up
    public void createEventButtonClick(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../create_activity_pop.fxml"));
            Parent createEventFXML = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene createEventScene = new Scene(createEventFXML);
            stage.setScene(createEventScene);
            stage.showAndWait();

        } catch (IOException e){
            e.printStackTrace();
        }
    }



    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
