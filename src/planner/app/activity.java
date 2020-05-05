package planner.app;

public class activity {
	
	static void displayactivity() {
		System.out.println("Choose menu item:");
		System.out.println("1. Current activity Information: ");
		System.out.println("2. Register hours");
		System.out.println("3. Request Assistance");
		System.out.println("4. Exit");
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
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
		
	}
	
	static void displayRegisterhour() {
		
	}
	
	static void displayReqAssistance() {
		
	}
	
}
