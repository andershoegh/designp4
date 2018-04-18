package Training.Controller;

import Controller.MenuController;
import javafx.event.ActionEvent;

public class TrainingOverviewController {
    MenuController controller = new MenuController();

    public void menuButtonClick(ActionEvent event){
        controller.menuNavigation(event);
    }
}
