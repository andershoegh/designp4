package Calendar.Controller;

import Calendar.Controller.MatchTablePropertyValueFactory.MDatePropertyValueFactory;
import Calendar.Controller.MatchTablePropertyValueFactory.MDayOfMonthPropertyValueFactory;
import Calendar.Controller.MatchTablePropertyValueFactory.MatchTitelPropertyValueFactory;
import Calendar.Controller.TrainingTablePropertyValueFactory.TDatePropertyValueFactory;
import Calendar.Controller.TrainingTablePropertyValueFactory.TDayOfMonthPropertyValueFactory;
import Calendar.Controller.TrainingTablePropertyValueFactory.TTimePropertyValueFactory;
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
import Training.Training;

public class CalendarController {

    private ObservableList<Match> matchList= FXCollections.observableArrayList();
    private ObservableList<Match> FiltMatchList = FXCollections.observableArrayList();

    private ObservableList<Training> trainingList = FXCollections.observableArrayList();
    private ObservableList<Training> FiltTrainingList = FXCollections.observableArrayList();


    private ObservableList<?> others = FXCollections.observableArrayList();


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

    @FXML private TableView<Training> TrainingTableView;
    @FXML private TableColumn<?,?>TDayInMonth;
    @FXML private TableColumn<?,?>TrainingDate;
    @FXML private TableColumn<?,?>TrainingTime;

    @FXML private Button OpretButton;


    //Running methods when scene gets loaded
    @FXML
    public void initialize() throws ParseException {
        date = new Date();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());

        matchList.clear();
        clearMatchTable();
        MSetCellTable();
        loadMatchFromDB();

        trainingList.clear();
        clearTrainingTable();
        TSetCellTable();
        loadTrainingFromDB();
    }


    //Clear the table view
    public void clearMatchTable(){matchTableView.getItems().clear();}

    // Retrieves data from appropriate match class constructor
    private void MSetCellTable(){
        MDayInMonth.setCellValueFactory(new MDayOfMonthPropertyValueFactory<>("date"));
        matchTitel.setCellValueFactory(new MatchTitelPropertyValueFactory<>("opponent"));
        matchDate.setCellValueFactory(new MDatePropertyValueFactory<>("date"));
        matchTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    //Loads data from Match table in DB
    private void loadMatchFromDB() throws ParseException {
        clearMatchTable();
        matchList.clear();

        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM matches");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                matchList.add(new Match(rs.getInt("match_id"),
                                        rs.getString("opponent"),
                                        rs.getString("date"),
                                        rs.getString("time"),
                                        rs.getBoolean("isHome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //looping though the matchList to add the relevant data to a filtered list, FiltMatchList, for the tableList
        for(int i=0; i< matchList.size();i++){
            //Parsing the date-string-variable to a datatype of date named localDate
            DateFormat format = new SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH);
            Date localDate = format.parse(matchList.get(i).getDate());

            //Checks if the month and year of the match is equal to the month and year of the calender
            if(localDate.getMonth() == date.getMonth() && localDate.getYear() == date.getYear()){
                FiltMatchList.add(matchList.get(i));
            }
        }

        // inputting retrieved data from db into table view
        matchTableView.setItems(FiltMatchList);
        SqlConnection.closeConnection();
    }


    //Clear the table view
    public void clearTrainingTable(){TrainingTableView.getItems().clear();}

    // Retrieves data from appropriate training class constructor
    private void TSetCellTable(){
        TDayInMonth.setCellValueFactory(new TDayOfMonthPropertyValueFactory<>("date"));
        TrainingDate.setCellValueFactory(new TDatePropertyValueFactory<>("date"));
        TrainingTime.setCellValueFactory(new TTimePropertyValueFactory<>("startTime"));
    }


    //Loads data from trainings table in DB
    private void loadTrainingFromDB() throws ParseException {
        clearTrainingTable();
        trainingList.clear();

        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM trainings");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                trainingList.add(new Training(rs.getInt("training_id"),
                        rs.getString("date"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getInt("program_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //looping though the trainingList to add the relevant data to a filtered list, FiltTrainingList, for the tableView
        for(int i=0; i< trainingList.size();i++){
            //Parsing the date-string-variable to a datatype of date named localDate
            DateFormat format = new SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH);
            Date localDate = format.parse(trainingList.get(i).getDate());

            //Checks if the month and year of the training is equal to the month and year of the calender
            if(localDate.getMonth() == date.getMonth() && localDate.getYear() == date.getYear()){
                FiltTrainingList.add(trainingList.get(i));
            }
        }

        // inputting retrieved data from db into table view
        TrainingTableView.setItems(FiltTrainingList);
        SqlConnection.closeConnection();
    }






    //Sets next month in calender
    public void NextMonthButtonClick() throws ParseException {
        matchList.clear();
        clearMatchTable();
        MSetCellTable();

        trainingList.clear();
        clearTrainingTable();
        TSetCellTable();


        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        date = c.getTime();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());

        loadMatchFromDB();
        loadTrainingFromDB();
    }

    //Sets prev month in calender
    public void PrevMonthButtonClick() throws ParseException {
        clearMatchTable();
        matchList.clear();
        MSetCellTable();

        clearTrainingTable();
        trainingList.clear();
        TSetCellTable();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        date = c.getTime();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());

        loadMatchFromDB();
        loadTrainingFromDB();
    }



    //Loader Create event pop-up
    public void createEventButtonClick(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../createActivityMenu.fxml"));
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



    public void deleteEventButtonClick() throws ParseException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../DeleteEvent.fxml"));
            Parent deleteEventFXML = loader.load();

            Stage stage = new Stage();
            // Prevents user interaction with other windows while popup is open
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Slet event");

            Scene deleteEventScene = new Scene(deleteEventFXML);
            stage.setScene(deleteEventScene);
            stage.showAndWait();

            DeleteEventController controller = loader.getController();

            if(matchTableView.getSelectionModel().getSelectedItem() != null){
                if(controller.getConfirmValue()){
                    matchTableView.getSelectionModel().getSelectedItem().delete();
                }
            }
            else if (TrainingTableView.getSelectionModel().getSelectedItem() != null){
                if(controller.getConfirmValue()){
                    TrainingTableView.getSelectionModel().getSelectedItem().delete();
                }
            }
            else {
                System.out.println("none selected or deleted");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            clearTrainingTable();
            clearMatchTable();
            loadTrainingFromDB();
            loadMatchFromDB();
        }
    }


}
