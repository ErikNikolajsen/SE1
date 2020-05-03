package planner.app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MenuController {
	
	
	// Events
	public void onEmployees(ActionEvent event) throws Exception {
		Parent recipesPath = FXMLLoader.load(getClass().getResource("EmployeesView.fxml"));
		Scene recipesScene = new Scene(recipesPath);
		Model.primaryStage.setScene(recipesScene);		
		Model.primaryStage.show();
	}
	
	public void onExit(ActionEvent event) {
		Platform.exit();
	}
}
