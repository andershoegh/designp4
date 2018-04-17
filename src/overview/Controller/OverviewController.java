package Overview.Controller;

import Controller.MenuController;
import javafx.event.ActionEvent;

public class OverviewController {



    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }



}
