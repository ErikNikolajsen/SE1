package planner.app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.input.MouseEvent;

public class MenuController {
	
	
	public void onExit(ActionEvent event) {
		Platform.exit();
	}
}
