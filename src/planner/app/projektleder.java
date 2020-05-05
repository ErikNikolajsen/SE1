package planner.app;

public class projektleder {
	
	public static void displayleader() {
		System.out.println("");
		System.out.println("Project Leader Menu");
		System.out.println("Choose menu item:");
		System.out.println("1. Create New Activity");
		System.out.println("2. Delete Existing Activity");
		System.out.println("3. See Daily Journal");
		System.out.println("4. Exit");
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				validInput = true;
			} else if (n.equals("2")) {
				validInput = true;
			} else if (n.equals("3")) {
				validInput = true;
			} else if (n.equals("4")) {
				validInput = true;
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
		System.out.println("Sickomode");
	}
}
