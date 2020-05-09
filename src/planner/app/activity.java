package planner.app;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

public class activity {
	
	public static LocalDate currentDay = LocalDate.now();
	
	static void displayActivity() {
		System.out.println("");
		System.out.println("Manage your Activities");
		System.out.println("Choose menu item:");
		System.out.println("1. Assigned activities");
		System.out.println("2. Register hours");
		System.out.println("3. See work log");
		System.out.println("4. Exit");
		
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
				worklogView();
			} else if (n.equals("4")){
				validInput = true;
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	}

	private static void worklogView() {
		seeWorklog();
		displayActivity();
	}

	private static void myActivityView() {
		seeMyactivity();
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
		
		System.out.println(registerHours(activity, spendMinutes));
		displayActivity();
	}

	
	// Events
	
	private static void seeMyactivity() {
		ArrayList<Integer> myActivities = DatabaseAPI.selectInt("allocatedEmployees WHERE employee = '" + Model.currentUser + "'", "activity");
		ArrayList<String> myActivityNames = new ArrayList<String>();
		ArrayList<String> startTime = new ArrayList<String>();
		ArrayList<String> endTime = new ArrayList<String>();
		ArrayList<String> expectedMinutes = new ArrayList<String>();
		ArrayList<Integer> nettoSpendMinutes = new ArrayList<Integer>();
		TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		
		for (int i = 0 ; i < myActivities.size() ; i++) {
			myActivityNames.add(DatabaseAPI.selectString("activities WHERE id = " + myActivities.get(i), "activityName").get(0));
			startTime.add(DatabaseAPI.selectString("activities WHERE id = " + myActivities.get(i), "startTime").get(0));
			endTime.add(DatabaseAPI.selectString("activities WHERE id = " + myActivities.get(i), "endTime").get(0));
			expectedMinutes.add(DatabaseAPI.selectString("activities WHERE id = " + myActivities.get(i), "expectedMinutes").get(0));
			
			int nettoSpendMinutesTemp = 0;
			ArrayList<Integer> allSpendMinutes = DatabaseAPI.selectInt("timeslot WHERE activity = " + myActivities.get(i), "spendMinutes");
			for (int n = 0 ; n < allSpendMinutes.size() ; n++) {
				nettoSpendMinutesTemp = nettoSpendMinutesTemp + allSpendMinutes.get(n);
			}
			nettoSpendMinutes.add(nettoSpendMinutesTemp);
		}
		
		System.out.format("%-4s %-35s %-9s %-9s %-15s %s %n", "ID", "Name", "Start", "End", "Allocated time", "Spend time");
		for (int i = 0 ; i < myActivities.size() ; i++) {
			System.out.format("%-4s %-35s %-9s %-9s %-15s %s %n", myActivities.get(i), myActivityNames.get(i), LocalDate.parse(startTime.get(i)).getYear() + "-W" + LocalDate.parse(startTime.get(i)).get(woy), LocalDate.parse(endTime.get(i)).getYear() + "-W" + LocalDate.parse(endTime.get(i)).get(woy), expectedMinutes.get(i), nettoSpendMinutes.get(i));
		}
		
	}
	
	public static String registerHours(int activityID, int spendMinutes) {
		
		// Error if no such activity exists
		if (!(DatabaseAPI.selectString("activities WHERE id = " + activityID, "activityName").size() == 1)) {
			return "Error: activity does not exist in the database";
		}
		
		int expectedMinutes = DatabaseAPI.selectInt("activities WHERE id = " + activityID, "expectedMinutes").get(0);
		int nettoSpendMinutes = 0;
		ArrayList<Integer> allSpendMinutes = DatabaseAPI.selectInt("timeslot WHERE activity = " + activityID, "spendMinutes");
		for (int i = 0 ; i < allSpendMinutes.size() ; i++) {
			nettoSpendMinutes = nettoSpendMinutes + allSpendMinutes.get(i);
		}
		nettoSpendMinutes = nettoSpendMinutes + spendMinutes;
		
		// Error if current day is not between activity start and end day
		if (currentDay.isBefore(LocalDate.parse(DatabaseAPI.selectString("activities WHERE id = " + activityID , "startTime").get(0))) || currentDay.isAfter(LocalDate.parse(DatabaseAPI.selectString("activities WHERE id = " + activityID , "endTime").get(0)))) {
			return "Error: the activity has not yet started or it has passed";
		// Error if accumulated spentMinutes are larger than expectedMinutes
		} else if (nettoSpendMinutes > expectedMinutes) {
			return "Error: total spend minutes exceeds allowed amount";
		// Error if currentUser is not allocated to the activity
		} else if (!(DatabaseAPI.selectInt("allocatedEmployees WHERE employee = '" + Model.currentUser + "'", "activity").contains(activityID))) {
			return "Error: you are not allocated to the activity";
		
		} else {
			String sql = "INSERT INTO timeslot (employee, activity, spendMinutes, day) " +
                      "VALUES ('" + Model.currentUser.toUpperCase() + "', " + activityID + ", " + spendMinutes + ", '" + currentDay + "');"; 
			DatabaseAPI.createStatement(sql);
			return "Success: the timeslot was successfully was added to the database";
		}
		
	}
	
	private static void seeWorklog() {
		ArrayList<String> date = DatabaseAPI.selectString("timeslot WHERE employee = '" + Model.currentUser + "' ORDER BY day DESC", "day");
		ArrayList<Integer> spendMinutes = DatabaseAPI.selectInt("timeslot WHERE employee = '" + Model.currentUser + "' ORDER BY day DESC", "spendMinutes");
		ArrayList<Integer> activity = DatabaseAPI.selectInt("timeslot WHERE employee = '" + Model.currentUser + "' ORDER BY day DESC", "activity");
		
		System.out.format("%-9s %-11s %s %n", "Activity", "Spend time", "Date");
		for (int i = 0 ; i < date.size() ; i++) {
			System.out.format("%-9s %-11s %s %n", activity.get(i), spendMinutes.get(i), date.get(i));
		}
	}
	
}




































