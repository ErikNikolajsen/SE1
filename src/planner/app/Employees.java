package planner.app;

import java.util.*;
	/*
	 * TODO
	 * Add make leader menu item
	 * deleteEmployee()
	 * 	- make error if the user deleted is a leader
	 * 	- delete all the related activities of the deleted user
	 */
public class Employees {
	
	public static void displayEmployees() {
		System.out.println("Choose menu item:");
		System.out.println("1. Add employee");
		System.out.println("2. Delete employee");
		System.out.println("3. Exit");
		
		
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				validInput = true;
				addEmployeeView();
			} else if (n.equals("2")) {
				validInput = true;
				deleteEmployeeView();
			} else if (n.equals("3")) {
				validInput = true;
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	
	}
	
	private static void addEmployeeView() {
		String name;
		String initials;
		
		System.out.println("Choose name");
		name = Model.scan.nextLine();
		System.out.println("Choose initials");
		initials = Model.scan.nextLine();
		addEmployee(name, initials);
		displayEmployees();
	}
	
	private static void deleteEmployeeView() {
		String initials;
		
		System.out.println("Choose the initials of the employee you wish to delete");
		initials = Model.scan.nextLine();
		deleteEmployee(initials);
		displayEmployees();
	}
	
	
	//Controller events
	private static void addEmployee(String name, String initials) {
		// Error if no name is entered
		if (name.equals("")) {
			System.out.println("Error: empty name string");
		
		// Error if no initials are entered
		} else if (initials.equals("")) {
			System.out.println("Error: empty initials string");
		
		// Error if initials are longer than 4 characters
		} else if (initials.length() > 4) {
			System.out.println("Error: initials must be less than 4 characters long");
		
		// Error if an employee in the database has the same initials
		} else if (SQLiteJDBC.selectEmployeesInitials().contains(initials.toUpperCase())) {
			System.out.println("Error: initials already exists in the database");
			
		// If no errors are found in the inserted data the employee is added to the database as a non-projectLeader
		} else {
			String sql = "INSERT INTO employees (initials,name) " +
                      "VALUES ('" + initials.toUpperCase() + "', '" + name + "');"; 
	
			SQLiteJDBC.createStatement(sql);
			System.out.println("Success: the employee " + initials.toUpperCase() + " was added to the database");
		}
	}
	
	private static void deleteEmployee(String initials) {
		if (!SQLiteJDBC.selectEmployeesInitials().contains(initials.toUpperCase())) {
			System.out.println("Error: no employee with those initials exists in the database");
		} else if (initials.toUpperCase().equals(Model.currentUser)) {
			System.out.println("Error: it is not possible to delete yourself");
		} else {
			String sql = "DELETE FROM employees WHERE initials = '" + initials.toUpperCase() + "';";
			
			SQLiteJDBC.createStatement(sql);
			System.out.println("Success: the employee " + initials.toUpperCase() + " was deleted from the database");
		}
	}
}


