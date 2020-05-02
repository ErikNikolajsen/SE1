package planner.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Model.primaryStage = primaryStage;
		
		Parent root = FXMLLoader.load(getClass().getResource("../gui/MenuView.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Softwarehuset");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	
}
