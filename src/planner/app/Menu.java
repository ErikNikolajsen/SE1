package planner.app;

import java.util.Scanner;

public class Menu {
	
	static void displayMenu() {
		System.out.println("Choose menu item:");
		System.out.println("1. Manage Employees");
		System.out.println("2. My Activity");
		System.out.println("3. Logout");
		
		
		Scanner scan = new Scanner(System.in);
		
		boolean validInput = false;
		while (validInput == false) {
			String n = scan.nextLine();
			if (n.equals("1") || n.toLowerCase().equals("manage employees")) {
				validInput = true;
			} else if (n.equals("2") || n.toLowerCase().equals("my activity")) {
				validInput = true;
				activity.displayactivity();
			} else if (n.equals("3") || n.toLowerCase().equals("logout")) {
				Model.currentUser = null;
				Login.displayLogin();
			} else {
				System.out.println("Error: input not valid");
			}
		}
		System.out.println("Success");
	}
}


