package Calendar.Controller;

import Calendar.Controller.MatchTablePropertyValueFactory.MDatePropertyValueFactory;
import Calendar.Controller.MatchTablePropertyValueFactory.MDayOfMonthPropertyValueFactory;
import Calendar.Controller.MatchTablePropertyValueFactory.MatchTitelPropertyValueFactory;
import Calendar.Controller.OtherTablePropertyValueFactory.ODayOfMonthPropertyValueFactory;
import Calendar.Controller.TrainingTablePropertyValueFactory.TDatePropertyValueFactory;
import Calendar.Controller.TrainingTablePropertyValueFactory.TDayOfMonthPropertyValueFactory;
import Calendar.Controller.TrainingTablePropertyValueFactory.TTimePropertyValueFactory;
import Controller.DeleteAble;
import Controller.MenuController;
import Match.Match;
import Other_Event.Other;
import SQL.SqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Training.Training;

public class CalendarController {

    private ObservableList<Match> matchList= FXCollections.observableArrayList();
    private ObservableList<Training> trainingList = FXCollections.observableArrayList();
    private ObservableList<Other> otherList = FXCollections.observableArrayList();

    private DeleteAble selectedItem = null;
    private Date date;
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

    // setting FXML IDs
    @FXML private Label menuTeamName;
    @FXML private Label MonthYearLabel;

    @FXML private TableView<Other>otherTableView;
    @FXML private TableColumn<?,?>ODayInMonth;
    @FXML private TableColumn<?,?>otherTitel;
    @FXML private TableColumn<?,?>otherTime;

    @FXML private TableView<Match>matchTableView;
    @FXML private TableColumn<?,?>MDayInMonth;
    @FXML private TableColumn<?,?>matchTitel;
    @FXML private TableColumn<?,?>matchDate;
    @FXML private TableColumn<?,?>matchTime;

    @FXML private TableView<Training> trainingTableView;
    @FXML private TableColumn<?,?>TDayInMonth;
    @FXML private TableColumn<?,?> trainingDate;
    @FXML private TableColumn<?,?> trainingTime;

    @FXML private Button DeleteButton;
    @FXML private Button editButton;


    //Running methods when scene gets loaded
    @FXML
    public void initialize() throws ParseException {

        date = new Date();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());

        MSetCellTable();
        loadMatchFromDB();
        updateMatchTable();

        TSetCellTable();
        loadTrainingFromDB();
        updateTrainingTable();

        OSetCellTable();
        loadOtherFromDB();
        updateOtherTable();

        trainingTableView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        matchTableView.getSelectionModel().clearSelection();
                        otherTableView.getSelectionModel().clearSelection();
                        DeleteButton.setDisable(false);
                        editButton.setDisable(false);

                    }
                });

        matchTableView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        trainingTableView.getSelectionModel().clearSelection();
                        otherTableView.getSelectionModel().clearSelection();
                        DeleteButton.setDisable(false);
                        editButton.setDisable(false);
                    }
                });


        otherTableView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedItem = newValue;
                        trainingTableView.getSelectionModel().clearSelection();
                        matchTableView.getSelectionModel().clearSelection();
                        DeleteButton.setDisable(false);
                        editButton.setDisable(false);
                    }
                });

        DeleteButton.setDisable(true);
        editButton.setDisable(true);
    }


    // Checks for double clicks, and opens edit window accordingly.
    private Training trainingTemp;
    private Date trainingLastClickTime;
    @FXML
    private void trainingHandleRowSelect() throws IOException {
        Training trow = trainingTableView.getSelectionModel().getSelectedItem();
        if (trow == null)
            return;
        if(trow != trainingTemp){
            trainingTemp = trow;
            trainingLastClickTime = new Date();
        } else if(trow == trainingTemp) {
            Date now = new Date();
            long diff = now.getTime() - trainingLastClickTime.getTime();
            if (diff < 300){ //another click registered in 300 millis
                System.out.println("--- Double Clicked on a row! Will open edit window. ---");
                editButtonClick();
            } else {
                trainingLastClickTime = new Date();
            }
        }
    }

    // Checks for double clicks, and opens edit window accordingly.
    private Match matchTemp;
    private Date matchLastClickTime;
    @FXML
    private void matchHandleRowSelect() throws IOException {
        Match mrow = matchTableView.getSelectionModel().getSelectedItem();
        if (mrow == null)
            return;
        if(mrow != matchTemp){
            matchTemp = mrow;
            matchLastClickTime = new Date();
        } else if(mrow == matchTemp) {
            Date now = new Date();
            long diff = now.getTime() - matchLastClickTime.getTime();
            if (diff < 300){ //another click registered in 300 millis
                System.out.println("--- Double Clicked on a row! Will open edit window. ---");
                editButtonClick();
            } else {
                matchLastClickTime = new Date();
            }
        }
    }

    // Checks for double clicks, and opens edit window accordingly.
    private Other otherTemp;
    private Date otherLastClickTime;
    @FXML
    private void otherHandleRowSelect() throws IOException {
        Other orow = otherTableView.getSelectionModel().getSelectedItem();
        if (orow == null)
            return;
        if(orow != otherTemp){
            otherTemp = orow;
            otherLastClickTime = new Date();
        } else if(orow == otherTemp) {
            Date now = new Date();
            long diff = now.getTime() - otherLastClickTime.getTime();
            if (diff < 300){ //another click registered in 300 millis
                System.out.println("--- Double Clicked on a row! Will open edit window. ---");
                editButtonClick();
            } else {
                otherLastClickTime = new Date();
            }
        }
    }

    //Match-table methods
    // Retrieves data from appropriate match class constructor
    private void MSetCellTable(){
        MDayInMonth.setCellValueFactory(new MDayOfMonthPropertyValueFactory<>("date"));
        matchTitel.setCellValueFactory(new MatchTitelPropertyValueFactory<>("opponent"));
        matchDate.setCellValueFactory(new MDatePropertyValueFactory<>("date"));
        matchTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    //Loads data from Match table in DB
    private void loadMatchFromDB() throws ParseException {
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
        SqlConnection.closeConnection();
    }

    // inputting retrieved data from db into table view
    private void updateMatchTable(){
        Collections.sort(matchList, new Comparator<Match>() {
            @Override
            public int compare(Match o1, Match o2) {
                return o1.getConvertedDate().compareTo(o2.getConvertedDate());
            }
        });
        matchTableView.setItems(matchList.filtered(match -> match.getConvertedDate().getMonth() == date.getMonth()
                && match.getConvertedDate().getYear() == date.getYear()));
    }



    //Training-table methods
    // Retrieves data from appropriate training class constructor
    private void TSetCellTable(){
        TDayInMonth.setCellValueFactory(new TDayOfMonthPropertyValueFactory<>("date"));
        trainingDate.setCellValueFactory(new TDatePropertyValueFactory<>("date"));
        trainingTime.setCellValueFactory(new TTimePropertyValueFactory<>("startTime"));
    }

    //Loads data from trainings table in DB
    private void loadTrainingFromDB() throws ParseException {
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
        SqlConnection.closeConnection();
    }

    // inputting retrieved data from db into table view
    private void updateTrainingTable(){
        Collections.sort(trainingList, new Comparator<Training>() {
            @Override
            public int compare(Training o1, Training o2) {
                return o1.getConvertedDate().compareTo(o2.getConvertedDate());
            }
        });

        trainingTableView.setItems(trainingList.filtered(training -> training.getConvertedDate().getMonth() == date.getMonth()
                && training.getConvertedDate().getYear() == date.getYear()));
    }


    //OtherEvents-table methods
    // Retrieves data from appropriate training class constructor
    private void OSetCellTable(){
        ODayInMonth.setCellValueFactory(new ODayOfMonthPropertyValueFactory<>("date"));
        otherTitel.setCellValueFactory(new PropertyValueFactory<>("name"));
        otherTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    //Loads data from trainings table in DB
    private void loadOtherFromDB() throws ParseException {
        otherList.clear();
        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM otherEvents");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                otherList.add(new Other(rs.getInt("other_id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("note")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Setting the team name in the menu
        menuTeamName.setText(SqlConnection.getTeamNameFromDB());

        SqlConnection.closeConnection();
    }

    // inputting retrieved data from db into table view
    private void updateOtherTable(){
        Collections.sort(otherList, new Comparator<Other>() {
            @Override
            public int compare(Other o1, Other o2) {
                return o1.getConvertedDate().compareTo(o2.getConvertedDate());
            }
        });

        otherTableView.setItems(otherList.filtered(other -> other.getConvertedDate().getMonth() == date.getMonth()
                && other.getConvertedDate().getYear() == date.getYear()));
    }



    //Sets next month in calender
    public void NextMonthButtonClick() throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        date = c.getTime();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());

        updateMatchTable();
        updateTrainingTable();
        updateOtherTable();
    }

    //Sets prev month in calender
    public void PrevMonthButtonClick() throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        date = c.getTime();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());

        updateMatchTable();
        updateTrainingTable();
        updateOtherTable();
    }



    //Loader Create event pop-up
    public void createEventButtonClick(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../createActivityMenu.fxml"));
            Parent createEventFXML = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            Scene createEventScene = new Scene(createEventFXML);
            stage.setScene(createEventScene);
            stage.showAndWait();

            loadTrainingFromDB();
            loadMatchFromDB();
            loadOtherFromDB();
            updateMatchTable();
            updateTrainingTable();
            updateOtherTable();

        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //Loader delete event pop-up
    public void deleteEventButtonClick() throws ParseException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../DeleteEvent.fxml"));
            Parent deleteEventFXML = loader.load();

            Stage stage = new Stage();
            // Prevents user interaction with other windows while popup is open
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Slet event");
            stage.getIcons().add(new Image("file:graphics/ball.png"));
            Scene deleteEventScene = new Scene(deleteEventFXML);
            stage.setScene(deleteEventScene);
            stage.showAndWait();

            DeleteEventController controller = loader.getController();

            if(selectedItem != null){
                if(controller.getConfirmValue()){
                    selectedItem.delete();

                    loadOtherFromDB();
                    loadMatchFromDB();
                    loadTrainingFromDB();
                    updateOtherTable();
                    updateTrainingTable();
                    updateMatchTable();
                }
            }
            else {
                System.out.println("none selected or deleted");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editButtonClick() throws IOException {
        if (selectedItem.getClass().getSimpleName().equals("Training")){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../EditActivityTraining.fxml"));
            EditActivityTrainingController controller = new EditActivityTrainingController();
            controller.initData(trainingTableView.getSelectionModel().getSelectedItem());
            Parent createActivityTrainingFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Rediger træning");
            stage.getIcons().add(new Image("file:graphics/ball.png"));

            Scene createTrainingScene = new Scene(createActivityTrainingFXML);

            stage.setScene(createTrainingScene);

            stage.showAndWait();

        }
        else if(selectedItem.getClass().getSimpleName().equals("Match")){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../EditActivityMatch.fxml"));
            EditActivityMatchController controller = new EditActivityMatchController();
            controller.initData(matchTableView.getSelectionModel().getSelectedItem());

            Parent createActivityMatchFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Rediger kamp");
            stage.getIcons().add(new Image("file:graphics/ball.png"));


            Scene createMatchScene = new Scene(createActivityMatchFXML);

            stage.setScene(createMatchScene);

            stage.showAndWait();
        }
        else if(selectedItem.getClass().getSimpleName().equals("Other")){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../EditActivityMisc.fxml"));
            EditActivityMiscController controller = new EditActivityMiscController();
            controller.initData(otherTableView.getSelectionModel().getSelectedItem());
            Parent createActivityMiscFXML = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Rediger kamp");
            stage.getIcons().add(new Image("file:graphics/ball.png"));

            Scene createMiscScene = new Scene(createActivityMiscFXML);

            stage.setScene(createMiscScene);

            stage.showAndWait();
        }
        try {
            loadOtherFromDB();
            loadMatchFromDB();
            loadTrainingFromDB();
            updateOtherTable();
            updateTrainingTable();
            updateMatchTable();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    //    // Menu buttons navigation
    private MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
