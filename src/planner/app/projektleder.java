package planner.app;

public class projektleder {
	
	public static void displayleader() {
		System.out.println("");
		System.out.println("Project Leader Menu");
		System.out.println("Choose menu item:");
		System.out.println("1. See Activities");
		System.out.println("2. Create New Activity");
		System.out.println("3. Delete Existing Activity");
		System.out.println("4. See Daily Journal");
		System.out.println("5. Exit");
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				validInput = true;
				projektleder.displayAllActivities();
			}else if (n.equals("2")) {
				validInput = true;
				projektleder.displayCreateActivity();
			} else if (n.equals("3")) {
				validInput = true;
				projektleder.displaydeleteActivity();
			} else if (n.equals("4")) {
				validInput = true;
				projektleder.displayJournal();
			} else if (n.equals("5")) {
				validInput = true;
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	}
	
	public static void displayAllActivities() {
		
	}
	
	public static void displayCreateActivity() {
		
	}
	
	public static void displaydeleteActivity() {
		
	}
	
	public static void displayJournal() {
		
	}
}
