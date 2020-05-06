package planner.app;

import java.util.ArrayList;
import java.util.Calendar;

public class Projects {
	
	public static void displayProjects() {
		System.out.println("");
		System.out.println("Manage Projects");
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
				seeProjectView();
				System.out.println("");
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
		seeProjectView();
		displayProjects();
	}
	
	private static void addProjectsView() {
		System.out.println("Choose project name:");
		String projectName = Model.scan.nextLine();
		
		ArrayList<String> availableEmployees = SQLiteJDBC.selectString("employees", "initials");
		System.out.print("Employees: ");
		for (int i = 0 ; i < availableEmployees.size() ; i++) {
			if (i != availableEmployees.size()-1) {
				System.out.print(availableEmployees.get(i) + ", ");
			} else {
				System.out.println(availableEmployees.get(i));
			}
		}
		System.out.println("Choose initials of project leader:");
		String leaderInitials = Model.scan.nextLine();
		addProject(projectName, leaderInitials);
		displayProjects();
	}
	
	private static void deleteProjectsView() {
		String number;
		
		System.out.println("Choose the project-number of the project you wish to delete");
		number = Model.scan.nextLine();
		System.out.println("Deleting project: " + number.toUpperCase());
		System.out.println("1. Confirm");
		System.out.println("2. Cancel");
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				deleteProject(number);
				validInput = true;
			} else if (n.contentEquals("2")){
				validInput = true;
			} else {
				System.out.println("Error: invalid input");
			}
		}
		displayProjects();
	}
	
	// Controller events
	private static void addProject(String name, String initials) {
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
		
		} else if (SQLiteJDBC.selectString("projects", "projectName").contains(name.toLowerCase())) {
			System.out.println("Error: project already exists in the database");
			
		} else if (!SQLiteJDBC.selectString("employees", "initials").contains(initials.toUpperCase())) {
			System.out.println("Error: invalid employeer initials");
			
		} else {
			String sql = "INSERT INTO projects (projectNumber, projectName, projectLeader) " +
                      "VALUES ('" + projectNumber + "', '" + name.toLowerCase() + "', '" + initials.toUpperCase() + "');";
				
			SQLiteJDBC.createStatement(sql);
			System.out.println("Success: the project '" + name + " : " + projectNumber + "' was added to the database");
			
			SQLiteJDBC.createStatement("UPDATE parameters SET serialNumber = " + thisSerial + " WHERE serialNumber = " + currentSerial + ";"); //increases serial number in database
			
		}
	}
	
	private static void seeProjectView() {
		ArrayList<Integer> projectNumber = SQLiteJDBC.selectInt("projects", "projectNumber");
		ArrayList<String> projectName = SQLiteJDBC.selectString("projects", "projectName");
		ArrayList<String> projectLeader = SQLiteJDBC.selectString("projects", "projectLeader");
		System.out.println("Current projects in the database:\n");
		System.out.format("%-7s %-12s %1s %n", "Number", "Leader", "Name");
		for (int i = 0 ; i < projectNumber.size() ; i++) {
			String projectLeaderStatus;
			if (projectLeader.get(i) == null) {
				projectLeaderStatus = "No leader";
			} else {
				projectLeaderStatus = projectLeader.get(i);
			}
			System.out.format("%-7s %-12s %1s %n",projectNumber.get(i), "(" + projectLeaderStatus + ")",projectName.get(i));
		}
	}
	
	private static void deleteProject(String number) {
		if (!SQLiteJDBC.selectString("projects", "projectNumber").contains(number)) {
			System.out.println("Error: no project with that project-number exists in the database");
		} else {
			
			String sql = "DELETE FROM projects WHERE projectNumber = '" + number + "';";
			SQLiteJDBC.createStatement(sql);
			System.out.println("Success: the project " + number + " was deleted from the database");
			
		}
	}
}
