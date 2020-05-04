package planner.app;

import java.util.Scanner;

public class Menu {
	
	static void displayMenu() {
		System.out.println("Choose menu item:");
		System.out.println("1. Manage Employees");
		
		
		Scanner scan = new Scanner(System.in);
		
		int x = 0;
		while (x == 0) {
			String n = scan.nextLine();
			if (n.equals("1") || n.toLowerCase().equals("manage employees")) {
				x = 1;
			} else {
				System.out.println("Error: input not valid");
			}
		}
		System.out.println("Success");
	}
}


