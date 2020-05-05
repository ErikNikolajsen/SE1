package planner.app;

public class activity {
	
	static void displayactivity() {
		System.out.println("");
		System.out.println("Manage your Activities");
		System.out.println("Choose menu item:");
		System.out.println("1. Assigned activities");
		System.out.println("2. Register hours");
		System.out.println("3. Request Assistance");
		System.out.println("4. Exit");
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				displayMyActivity();
				validInput = true;
			} else if (n.equals("2")) {
				validInput = true;
			} else if (n.equals("3")){
				validInput = true;
			} else if (n.equals("4")) {
				validInput = true;
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	}
	
	static void displayMyActivity() {
		System.out.println("you are asigned to the following activities: ");
		//asigned activities displayed here:
		System.out.println("   You are currently not assigned to any activities");
		
		System.out.println("Choose menu item:");
		System.out.println("1. Exit");
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				activity.displayactivity();
			} else if (n.equals("2")) {
				System.out.println("Error: invalid input");
			}
		}
	}
	
	static void displayRegisterhour() {
		
	}
	
	static void displayReqAssistance() {
		
	}
	
}
