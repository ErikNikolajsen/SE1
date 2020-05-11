/*///////////////////////
 * 
 *    Author:
 *    Erik Ravn Nikolajsen
 *    s144382
 * 
 *///////////////////////

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
		
		ArrayList<String> availableEmployees = DatabaseAPI.selectString("employees", "initials");
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
		System.out.println(addProject(projectName, leaderInitials));
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
				System.out.println(deleteProject(number));
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
	public static String addProject(String name, String initials) {
		String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		String abbreviatedYear = year.substring(year.length()-2);
		int currentSerial = DatabaseAPI.selectInt("parameters", "serialNumber").get(0);
		int thisSerial = currentSerial+1;
		String serialNumber = Integer.toString(thisSerial);
		if (serialNumber.length() == 1) {
			serialNumber = "000" + serialNumber;
		} else if (serialNumber.length() == 2) {
			serialNumber = "00" + serialNumber;
		} else if (serialNumber.length() == 3) {
			serialNumber = "0" + serialNumber;
		} 
		String projectNumber = abbreviatedYear + serialNumber;
		
		if (name.equals("")) {
			return "Error: empty name string";
		
		} else if (DatabaseAPI.selectString("projects", "projectName").contains(name.toLowerCase())) {
			return "Error: project already exists in the database";
			
		} else if (!DatabaseAPI.selectString("employees", "initials").contains(initials.toUpperCase())) {
			return "Error: invalid employeer initials";
			
		} else {
			String sql = "INSERT INTO projects (projectNumber, projectName, projectLeader) " +
                      "VALUES ('" + projectNumber + "', '" + name.toLowerCase() + "', '" + initials.toUpperCase() + "');";
				
			DatabaseAPI.createStatement(sql);
			DatabaseAPI.createStatement("UPDATE parameters SET serialNumber = " + thisSerial + " WHERE serialNumber = " + currentSerial + ";"); //increases serial number in database
			return "Success: the project '" + name + "' was added to the database";
		}
	}
	
	private static void seeProjectView() {
		ArrayList<Integer> projectNumber = DatabaseAPI.selectInt("projects", "projectNumber");
		ArrayList<String> projectName = DatabaseAPI.selectString("projects", "projectName");
		ArrayList<String> projectLeader = DatabaseAPI.selectString("projects", "projectLeader");
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
	
	public static String deleteProject(String number) {
		if (!DatabaseAPI.selectString("projects", "projectNumber").contains(number)) {
			return "Error: no project with that project-number exists in the database";
		} else {
			String sql = "DELETE FROM projects WHERE projectNumber = '" + number + "';";
			DatabaseAPI.createStatement(sql);
			return "Success: the project '" + number + "' was deleted from the database";
		}
	}
}
















