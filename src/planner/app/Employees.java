package planner.app;

import java.util.*;

public class Employees {
	
	public static void displayEmployees() {
		System.out.println("Choose menu item:");
		System.out.println("1. See employees");
		System.out.println("2. Add employee"); 
		System.out.println("3. Delete employee");
		System.out.println("4. Exit");
		
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				validInput = true;
				seeEmployees();
			} else if (n.equals("2")) {
				validInput = true;
				addEmployeeView();
			} else if (n.equals("3")) {
				validInput = true;
				deleteEmployeeView();
			} else if (n.equals("4")) {
				validInput = true;
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	
	}
	
	private static void addEmployeeView() {
		System.out.println("Choose employee name");
		String name = Model.scan.nextLine();
		System.out.println("Choose employee initials");
		String initials = Model.scan.nextLine();
		System.out.println(addEmployee(name, initials));
		displayEmployees();
	}
	
	private static void deleteEmployeeView() {
		String initials;
		
		System.out.println("Choose the initials of the employee you wish to delete");
		initials = Model.scan.nextLine();
		System.out.println("Deleting user: " + initials.toUpperCase());
		System.out.println("1. Confirm");
		System.out.println("2. Cancel");
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				System.out.println(deleteEmployee(initials));
				validInput = true;
			} else if (n.contentEquals("2")){
				validInput = true;
			} else {
				System.out.println("Error: invalid input");
			}
		}
		displayEmployees();
	}
	
	
	//Controller events
	public static String addEmployee(String name, String initials) {
		// Error if no name is entered
		if (name.equals("")) {
			return "Error: empty name string";
		
		// Error if no initials are entered
		} else if (initials.equals("")) {
			return "Error: empty initials string";
		
		// Error if initials are longer than 4 characters
		} else if (initials.length() > 4) {
			return "Error: initials must be less than 5 characters long";
		
		// Error if an employee in the database has the same initials
		} else if (DatabaseAPI.selectString("employees", "initials").contains(initials.toUpperCase())) {
			return "Error: initials already exists in the database";
			
		// If no errors are found in the inserted data the employee is added to the database as a non-projectLeader
		} else {
			String sql = "INSERT INTO employees (initials,name) " +
                      "VALUES ('" + initials.toUpperCase() + "', '" + name + "');"; 
	
			DatabaseAPI.createStatement(sql); 
			return "Success: the employee " + initials.toUpperCase() + " was added to the database";
		}
	}
	
	public static String deleteEmployee(String initials) {
		if (!DatabaseAPI.selectString("employees", "initials").contains(initials.toUpperCase())) {
			return "Error: no employee with those initials exists in the database";
		} else if (initials.toUpperCase().equals(Model.currentUser)) {
			return "Error: it is not possible to delete yourself";
		} else {
			String sql = "DELETE FROM employees WHERE initials = '" + initials.toUpperCase() + "';";
			
			DatabaseAPI.createStatement(sql);
			return "Success: the employee " + initials.toUpperCase() + " was deleted from the database";
		}
	}
	
	private static void seeEmployees() {
		System.out.println("Current employees in database: ");
		ArrayList<String> initials = DatabaseAPI.selectString("employees", "initials");
		ArrayList<String> names = DatabaseAPI.selectString("employees", "name");
		
		for (int i = 0 ; i < initials.size() ; i++) {
			System.out.format("%-6s%1s%n", initials.get(i),  names.get(i));
		}
		System.out.println();
		Employees.displayEmployees();
	}
}


