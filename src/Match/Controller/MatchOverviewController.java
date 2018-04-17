package Match.Controller;

import Controller.MenuController;
import javafx.event.ActionEvent;

public class MatchOverviewController {

    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
