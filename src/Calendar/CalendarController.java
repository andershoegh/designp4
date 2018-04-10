package Calendar;

import Controller.MenuController;
import javafx.event.ActionEvent;

public class CalendarController {

    // Menu buttons navigation
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
