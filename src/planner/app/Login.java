package planner.app;

import java.util.*;

public class Login {
	
	public static void login() {
	System.out.println("Please choose a user:");
	
	ArrayList<String> initials = SQLiteJDBC.selectEmployeesInitials();
		for (int i = 0 ; i < initials.size() ; i++) {
			if (i == initials.size()-1) {
				System.out.print(initials.get(i) + "\n");
			} else {
				System.out.print(initials.get(i) + ", ");
			}
		}
	
	
	Scanner scan = new Scanner(System.in);
	
	
	int x = 0;
	while (x == 0) {
		String n = scan.nextLine();
		if (initials.contains(n.toUpperCase())) {
			Model.currentUser = n.toUpperCase();
			x = 1;
		} else {
			System.out.println("Login failure");
		}
	}
	Menu.displayMenu();
	}
	
	
}
