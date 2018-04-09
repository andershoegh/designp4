package Player.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import SQL.SqlConnection;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class statisticsController {
/*
    // This is a connection between the FXML file and the DB, inserting values.
    @FXML
    private TableView stats_player_tableview;
    @FXML
    private TableColumn stats_player_number_tablecolumn;
    @FXML
    private TableColumn stats_player_tablecolumn;
    @FXML
    private TableColumn stats_player_goals_tablecolumn;
    @FXML
    private TableColumn stats_player_assists_tablecolumn;

    public class input_stats_player_tableview {

        Connection conn = SqlConnection.connectToDB();

        String sql = "SELECT " + "(name, goalScored, assist)" + " FROM player ";


            try

        {
            PreparedStatement stmt = conn.prepareStatement(sql);


        } catch(
        SQLException e)

        {
            System.out.println(e.getMessage());
        } catch(
        IOException e)

        {
            e.printStackTrace();
        }

            SqlConnection.closeConnection();

    }
*/
}
