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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarController {

    private ObservableList<Match> matchList;
    private ObservableList<?> trainings;
    private ObservableList<?> others;

    private Date date;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

    // setting FXML IDs
    @FXML private Label MonthYearLabel;
    @FXML private Button PrevMonthButton;
    @FXML private Button NextMonthButton;
    @FXML private ListView<String> matchListView;
    @FXML private ListView<?> TrainingListView;
    @FXML private ListView<?> OtherListView;
    @FXML private Button OpretButton;


    @FXML
    public void initialize(){

        date = new Date();
        MonthYearLabel.setText(sdf.format(date).toUpperCase());
        System.out.println(date);

        matchList = FXCollections.observableArrayList();
        loadMatchFromDB();

        //setCellTable();
        //loadDataFromDB();

        trainings = FXCollections.observableArrayList();
        //setCellTable();
        //loadDataFromDB();

        others = FXCollections.observableArrayList();
        //setCellTable();
        //loadDataFromDB();
    }

    private void loadMatchFromDB(){
       int compMonth= date.getMonth()+1;
        System.out.println(compMonth);

        try {
            Connection conn = SqlConnection.connectToDB();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM matches");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                matchList.add(new Match(rs.getString("opponent")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(matchList.size());
        ObservableList<String> opponentList = FXCollections.observableArrayList();

        for(int i=0; i< matchList.size();i++){
            System.out.println(matchList.get(i).getOpponent());
            opponentList.add(matchList.get(i).getOpponent());
        }
        // inputting retrieved data from db into table row
        matchListView.setItems(opponentList);

        SqlConnection.closeConnection();
    }


    public void NextMonthButtonClick(){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        date = c.getTime();

        MonthYearLabel.setText(sdf.format(date).toUpperCase());
        System.out.println(date);
    }

    public void PrevMonthButtonClick(){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        date = c.getTime();

        MonthYearLabel.setText(sdf.format(date).toUpperCase());
        System.out.println(date);
    }

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
