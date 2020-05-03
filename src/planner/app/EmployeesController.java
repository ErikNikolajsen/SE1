package planner.app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class EmployeesController {

	public void onExit(ActionEvent event) {
		Platform.exit();
	}
	
}
