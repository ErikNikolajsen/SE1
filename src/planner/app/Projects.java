package planner.app;

import java.util.Calendar;

public class Projects {
	
	public static void displayProjects() {
		System.out.println("Choose menu item:");
		System.out.println("1. See projects");
		System.out.println("2. Add projects");
		System.out.println("3. Delete projects");
		System.out.println("4. Exit");
		
		
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				validInput = true;
				seeProjectsView();
			} else if (n.equals("2")) {
				validInput = true;
				addProjectsView();
			} else if (n.equals("3")) {
				validInput = true;
				deleteProjectsView();
			} else if (n.equals("4")) {
				validInput = true;
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	
	}

	private static void seeProjectsView() {
		// TODO Auto-generated method stub
		
	}
	
	private static void addProjectsView() {
		System.out.println("Choose project name");
		String name = Model.scan.nextLine();
		addProject(name);
	}
	
	private static void deleteProjectsView() {
		// TODO Auto-generated method stub
		
	}
	
	// Controller events
	private static void addProject(String name) {
		String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		String abbreviatedYear = Integer.toString(year.charAt(-2)) + Integer.toString(year.charAt(-1));
		
		
		
		
		// Error if no name is entered
		if (name.equals("")) {
			System.out.println("Error: empty name string");
		
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
	
}
