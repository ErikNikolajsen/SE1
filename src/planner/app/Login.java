package planner.app;

import java.util.*;

public class Login {
	
	public static void displayLogin() {
		System.out.println("Please choose a user:");
		
		ArrayList<String> initials = SQLiteJDBC.selectEmployeesInitials();
			for (int i = 0 ; i < initials.size() ; i++) {
				if (i == initials.size()-1) {
					System.out.print(initials.get(i) + "\n");
				} else {
					System.out.print(initials.get(i) + ", ");
				}
			}
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (initials.contains(n.toUpperCase())) {
				validInput = true;
				Model.currentUser = n.toUpperCase();
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	
	}
	
	
}
