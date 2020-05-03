package planner.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class EmployeesController {
	
	// Defining FXML elements
	@FXML private TextField name;
	@FXML private TextField initials;
	
	// Functions
	private void addEmployee() {
		Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:test.db");
	         c.setAutoCommit(false);
	         System.out.println("Opened database successfully");

	         stmt = c.createStatement();
	         String sql = "INSERT INTO developer (initials,name) " +
	                        "VALUES ('" + initials.getText() + "', '" + name.getText() + "' );"; 
	         stmt.executeUpdate(sql);

	         stmt.close();
	         c.commit();
	         c.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Records created successfully");
	}
	
	
	// Events
	public void onAddEmployee(ActionEvent event) {
		System.out.println(name.getText());
		System.out.println(initials.getText());
		addEmployee();
	}
	
}
