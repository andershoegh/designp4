package Calendar.Controller;

import Controller.MenuController;
import Match.Match;
import SQL.SqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private ObservableList<Match> FiltMatchList = FXCollections.observableArrayList();

    private Date date;
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

    // setting FXML IDs
    @FXML private Label MonthYearLabel;
    @FXML private Button PrevMonthButton;
    @FXML private Button NextMonthButton;

    @FXML private TableView<?>OtherTableView;
    @FXML private TableColumn<?,?>ODayInMonth;
    @FXML private TableColumn<?,?>OtherTitel;
    @FXML private TableColumn<?,?>OtherTime;

    @FXML private TableView<Match>matchTableView;
    @FXML private TableColumn<?,?>MDayInMonth;
    @FXML private TableColumn<?,?>matchTitel;
    @FXML private TableColumn<?,?>matchDate;
    @FXML private TableColumn<?,?>matchTime;

    @FXML private TableView<?>TrainingTableView;
    @FXML private TableColumn<?,?>TDayInMonth;
    @FXML private TableColumn<?,?>TrainingWeekDay;
    @FXML private TableColumn<?,?>TrainingDate;
    @FXML private TableColumn<?,?>TrainingTime;

    @FXML private Button OpretButton;


    //Running methods when scene gets loaded
    @FXML
    public void initialize() throws ParseException {
        date = new Date();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());
        setCellTable();
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
                matchList.add(new Match(rs.getString("opponent"),
                                        rs.getString("date"),
                                        rs.getString("time"),
                                        rs.getBoolean("home_away")));
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
                FiltMatchList.add(matchList.get(i));
            }
        }

        // inputting retrieved data from db into list view
        matchTableView.setItems(FiltMatchList);
        SqlConnection.closeConnection();
    }



    // Retrieves data from appropriate player class constructor
    private void setCellTable(){
        MDayInMonth.setCellValueFactory(new PropertyValueFactory<>("date"));
        matchTitel.setCellValueFactory(new MatchTitelPropertyValueFactory<>("opponent"));
        matchDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        matchTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }


    //Sets next month in calender
    public void NextMonthButtonClick() throws ParseException {
        matchList.clear();
        clearMatchList();
        setCellTable();

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
        setCellTable();

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../create_activity_pop.fxml"));
            Parent createEventFXML = loader.load();

            Scene createActivityScene = new Scene(createEventFXML);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setScene(createActivityScene);
            stage.show();

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
