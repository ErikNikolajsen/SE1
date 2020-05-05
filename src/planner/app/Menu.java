package planner.app;


public class Menu {
	
	static void displayMenu() {
		System.out.println("Choose menu item:");
		System.out.println("1. Manage Employees");
		System.out.println("1. Manage Projects");
		System.out.println("3. My Activity");
		System.out.println("4. Logout");
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1") || n.toLowerCase().equals("manage employees")) {
				Employees.displayEmployees();
				validInput = true;
			} else if (n.equals("2")) {
				validInput = true;
				Projects.displayProjects();
			} else if (n.equals("3") || n.toLowerCase().equals("my activity")) {
				validInput = true;
				activity.displayactivity();
			} else if (n.equals("4") || n.toLowerCase().equals("logout")) {
				Model.currentUser = null;
				Login.displayLogin();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	}
}


