package planner.app;


public class Menu {
	
	static void displayMenu() {
		System.out.println("Choose menu item:");
		System.out.println("1. Manage Employees");
		System.out.println("2. My Activity");
		System.out.println("3. Logout");
		
		
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				validInput = true;
			} else if (n.equals("2") || n.toLowerCase().equals("my activity")) {
				validInput = true;
				activity.displayactivity();
			} else if (n.equals("3") || n.toLowerCase().equals("logout")) {
				Employees.displayEmployees();
			} else if (n.equals("2")) {
				Model.currentUser = null;
				Login.displayLogin();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	}
}


