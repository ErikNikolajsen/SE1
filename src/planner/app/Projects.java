package planner.app;

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
		// TODO Auto-generated method stub
		
	}
	
	private static void deleteProjectsView() {
		// TODO Auto-generated method stub
		
	}
	
	// Controller events
	
	
}
