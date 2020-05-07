package planner.app;

import java.time.LocalDate;
import java.util.ArrayList;

public class activity {
	
	static void displayActivity() {
		System.out.println("");
		System.out.println("Manage your Activities");
		System.out.println("Choose menu item:");
		System.out.println("1. Assigned activities");
		System.out.println("2. Register hours");
		System.out.println("3. Exit");
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				myActivityView();
				validInput = true;
			} else if (n.equals("2")) {
				validInput = true;
				registerHoursView();
			} else if (n.equals("3")){
				validInput = true;
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	}

	private static void myActivityView() {
		//seeMyactivity();
		displayActivity();
	}
	
	private static void registerHoursView() {
		System.out.println("Input activity ID:");
		while(!Model.scan.hasNextInt()) {
		    Model.scan.next();
		}
		int activity = Model.scan.nextInt();
		System.out.println("Input spend minutes:");
		while(!Model.scan.hasNextInt()) {
		    Model.scan.next();
		}
		int spendMinutes = Model.scan.nextInt();
		
		registerHours(activity, spendMinutes);
		displayActivity();
	}

	
	// Events
	
//	private static void seeMyactivity() {
//		ArrayList<Integer> myActivities = DatabaseAPI.selectInt("allocatedEmployees WHERE employee = '" + Model.currentUser + "'", "activity");
//		ArrayList<String> myActivityNames = null;
//		for (int i = 0 ; i < myActivities.size() ; i++) {
//			myActivityNames.add(DatabaseAPI.selectString("activities WHERE id = " + myActivities.get(i), "activityName").get(0));
//		}
//		
//		for (int i = 0 ; i < myActivities.size() ; i++) {
//			System.out.print(myActivities.get(i) + ", " + myActivityNames.get(i));
//		}
//		
//		
//		
//		//System.out.format("%-4s %-35s %-12s %-11s %-11s %s %n", "ID", "Name", "Minutes", "Start", "End", "");
//	}
	
	private static void registerHours(int activityID, int spendMinutes) {
		int expectedMinutes = DatabaseAPI.selectInt("activities WHERE id = " + activityID, "expectedMinutes").get(0);
		int nettoSpendMinutes = 0;
		ArrayList<Integer> allSpendMinutes = DatabaseAPI.selectInt("timeslot WHERE activity = " + activityID, "spendMinutes");
		for (int i = 0 ; i < allSpendMinutes.size() ; i++) {
			nettoSpendMinutes = nettoSpendMinutes + allSpendMinutes.get(i);
		}
		nettoSpendMinutes = nettoSpendMinutes + spendMinutes;
		
		
		LocalDate currentDay = LocalDate.now();
		// Error if no such activity exists
		if (!(DatabaseAPI.selectString("activities WHERE id = " + activityID, "activityName").size() == 1)) {
			System.out.println("Error: activity does not exist in the database");
		// Error if current day is not between activity start and end day
		} else if (currentDay.isBefore(LocalDate.parse(DatabaseAPI.selectString("activities WHERE id = " + activityID , "startTime").get(0))) || currentDay.isAfter(LocalDate.parse(DatabaseAPI.selectString("activities WHERE id = " + activityID , "endTime").get(0)))) {
			System.out.println("Error: the activity has not yet started or it has passed");
		// Error if accumulated spentMinutes are larger than expectedMinutes
		} else if (nettoSpendMinutes > expectedMinutes) {
			System.out.println("Error: total spend minutes exceeds allowed amount");
		
		} else {
			String sql = "INSERT INTO timeslot (employee, activity, spendMinutes, day) " +
                      "VALUES ('" + Model.currentUser.toUpperCase() + "', " + activityID + ", " + spendMinutes + ", " + currentDay + ");"; 
			DatabaseAPI.createStatement(sql);
			System.out.println("Success: the timeslot was successfully was added to the database");
		}
		
	}
	
}




































