package planner.app;


public class Menu {
	
	static void displayMenu() {
		System.out.println("Choose menu item:");
		System.out.println("1. Manage Employees");
		System.out.println("2. Logout");
		
		
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				validInput = true;
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


