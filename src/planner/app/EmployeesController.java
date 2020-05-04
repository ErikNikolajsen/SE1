//package planner.app;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.TextField;
//import javafx.scene.control.Label;
//
//public class EmployeesController {
//	
//	// Defining FXML elements
//	@FXML private TextField name;
//	@FXML private TextField initials;
//	@FXML private Label notification;
//	
//	
//	// Events
//	public void onAddEmployee(ActionEvent event) {
//		
//		// Error if no name is entered
//		if (name.getText().equals("")) {
//			notification.setText("Error: please enter a name");
//		
//		// Error if no initials are entered
//		} else if (initials.getText().equals("")) {
//			notification.setText("Error: please enter initials");
//		
//		// Error if initials are longer than 4 characters
//		} else if (initials.getText().length() > 4) {
//			notification.setText("Error: initials must be less than 4 characters long");
//		
//		// Error if an employee in the database has the same initials
//		} else if (SQLiteJDBC.selectEmployeesInitials().contains(initials.getText())) {
//			notification.setText("Error: initials already exists in the database");
//			
//		// If no errors are found in the inserted data the employee is added to the database as a non-projectLeader
//		} else {
//			String sql = "INSERT INTO employees (initials,name) " +
//                         "VALUES ('" + initials.getText().toUpperCase() + "', '" + name.getText() + "');"; 
//	
//			SQLiteJDBC.createStatement(sql);
//			notification.setText("Succes: the employee " + name.getText() + "was added to the database");
//		}
//	}
//}
