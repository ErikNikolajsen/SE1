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
		System.out.println("Choose project name:");
		String name = Model.scan.nextLine();
		addProject(name);
	}
	
	private static void deleteProjectsView() {
		// TODO Auto-generated method stub
		
	}
	
	// Controller events
	private static void addProject(String name) {
		String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		String abbreviatedYear = year.substring(year.length()-2);
		int currentSerial = SQLiteJDBC.selectInt("parameters", "serialNumber").get(0);
		int thisSerial = currentSerial+1;
		String serialNumber = Integer.toString(thisSerial);
		if (serialNumber.length() == 1) {
			serialNumber = "000" + serialNumber;
		} else if (serialNumber.length() == 2) {
			serialNumber = "00" + serialNumber;
		} else if (serialNumber.length() == 3) {
			serialNumber = "0" + serialNumber;
		} else if (serialNumber.length() != 4) {
			System.out.println("Error: the database cannot contain more projects");
			displayProjects();
		}
		String projectNumber = abbreviatedYear + serialNumber;
		
		if (name.equals("")) {
			System.out.println("Error: empty name string");
			displayProjects();
		
		} else if (SQLiteJDBC.selectString("projects", "projectName").contains(name.toLowerCase())) {
			System.out.println("Error: project already exists in the database");
			displayProjects();
		
		} else {
			String sql = "INSERT INTO projects (projectNumber, projectName) " +
                      "VALUES ('" + projectNumber + "', '" + name.toLowerCase() + "');"; 
	
			SQLiteJDBC.createStatement(sql);
			System.out.println("Success: the project '" + name + " : " + projectNumber + "' was added to the database");
			
			SQLiteJDBC.createStatement("UPDATE parameters SET serialNumber = " + thisSerial + " WHERE serialNumber = " + currentSerial + ";"); //increases serial number in database
			displayProjects();
		}
	}
	
}
