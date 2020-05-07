package planner.app;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProjectLeader {
	
	private static ArrayList<String> usersProjects;
	private static String openedProject;
	
	public static void chooseProject() {
		usersProjects = DatabaseAPI.selectString("projects WHERE projectLeader = '" + Model.currentUser + "'", "projectNumber");
		openedProject = null;
		
		if (usersProjects.size() == 1) {
			openedProject = usersProjects.get(0);
			displayProjectLeader();
		} else {
			System.out.println("Choose project number:");
			for (int i = 0 ; i < usersProjects.size() ; i++) {
				System.out.println(usersProjects.get(i) + " " + DatabaseAPI.selectString("projects WHERE projectNumber = " + usersProjects.get(i), "projectName").get(0));
			}
			while (true) {
				String enterProject = Model.scan.nextLine();
				if (usersProjects.contains(enterProject)) {
					openedProject = enterProject;
					displayProjectLeader();
				}
			}
		}
		
	}
	
	public static void displayProjectLeader() {
		System.out.println("");
		System.out.println("Project: " + openedProject + " - " + DatabaseAPI.selectString("projects WHERE projectNumber = " + openedProject, "projectName").get(0));
		System.out.println("Project Leader Menu");
		System.out.println("Choose menu item:");
		System.out.println("1. See Activities");
		System.out.println("2. Add Activity");
		System.out.println("3. Delete Activity");
		System.out.println("4. Allocate employee");
		System.out.println("5. Deallocate employee");
		System.out.println("6. See Daily Journal");
		System.out.println("7. Exit");
		
		boolean validInput = false;
		while (validInput == false) {
			String n = Model.scan.nextLine();
			if (n.equals("1")) {
				validInput = true;
				ProjectLeader.seeActivitiesView();
			}else if (n.equals("2")) {
				validInput = true;
				ProjectLeader.addActivityView();
			} else if (n.equals("3")) {
				validInput = true;
				ProjectLeader.deleteActivityView();
			} else if (n.equals("4")) {
				validInput = true;
				ProjectLeader.allocateEmployeeView();
			} else if (n.equals("5")) {
				validInput = true;
				ProjectLeader.deallocateEmployeeView();
			} else if (n.equals("6")) {
				validInput = true;
				ProjectLeader.displayJournalView();
			} else if (n.equals("7")) {
				validInput = true;
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
	}
	
	private static void deallocateEmployeeView() {
		System.out.println("Choose employee initials:");
		String employeeInitials = Model.scan.nextLine();
		
		System.out.println("Choose activity id:");
		while(!Model.scan.hasNextInt()) {
		    Model.scan.next();
		}
		int activityID = Model.scan.nextInt();
		
		deallocateEmployee(employeeInitials, activityID);
		displayProjectLeader();
		
	}

	private static void allocateEmployeeView() {
		System.out.println("Choose employee initials:");
		String employeeInitials = Model.scan.nextLine();
		
		System.out.println("Choose activity id:");
		while(!Model.scan.hasNextInt()) {
		    Model.scan.next();
		}
		int activityID = Model.scan.nextInt();
		
		allocateEmployee(employeeInitials, activityID);
		displayProjectLeader();
	}

	public static void seeActivitiesView() {
		seeActivities();
		displayProjectLeader();
	}
	
	public static void addActivityView() {
		System.out.println("Choose activity name:");
		String name = Model.scan.nextLine();
		
		System.out.println("Input expected time required in minutes:");
		while(!Model.scan.hasNextInt()) {
		    Model.scan.next();
		}
		int expectedMinutes = Model.scan.nextInt();
		
		System.out.println("Input start year:");
		while(!Model.scan.hasNextInt()) {
		    Model.scan.next();
		}
		int startTimeYear = Model.scan.nextInt();
		
		System.out.println("Input start week:");
		while(!Model.scan.hasNextInt()) {
		    Model.scan.next();
		}
		int startTimeWeek = Model.scan.nextInt();
		
		System.out.println("Input end year:");
		while(!Model.scan.hasNextInt()) {
		    Model.scan.next();
		}
		int endTimeYear = Model.scan.nextInt();
		
		System.out.println("Input end week:");
		while(!Model.scan.hasNextInt()) {
		    Model.scan.next();
		}
		int endTimeWeek = Model.scan.nextInt();
		
		addActivity(name, expectedMinutes, startTimeYear, startTimeWeek, endTimeYear, endTimeWeek);
		displayProjectLeader();
	}
	
	public static void deleteActivityView() {
		System.out.println("Choose the activity you wish to delete");
		String activity = Model.scan.nextLine();
		deleteActivity(activity);
		displayProjectLeader();
	}

	public static void displayJournalView() {
		
	}
	
	// Events
	
	private static void addActivity(String name, int expectedMinutes, int startTimeYear, int startTimeWeek, int endTimeYear, int endTimeWeek) {
		//Get Monday date of year and week number
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, startTimeYear);
		calendar.set(Calendar.WEEK_OF_YEAR, startTimeWeek);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		LocalDate startTime = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		//Get Sunday date of year and week number
		calendar.set(Calendar.YEAR, endTimeYear);
		calendar.set(Calendar.WEEK_OF_YEAR, endTimeWeek);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		LocalDate endTime = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		
		
		// Error if empty name string
		if (name.equals("")) {
			System.out.println("Error: empty name string");
		
		// Error if an activity startTime is after endTime
		} else if (endTime.isBefore(startTime)) {
			System.out.println("Error: activity start-date is after the end-date");
		
		// Error if an activity in the database has the same name
		} else if (DatabaseAPI.selectString("activities", "activityName").contains(name.toLowerCase())) {
			System.out.println("Error: activity name already exists in the database");
			
		// If no errors are found in the inserted data the employee is added to the database as a non-projectLeader
		} else {
			String sql = "INSERT INTO activities (activityName, expectedMinutes, startTime, endTime, project) " +
                      "VALUES ('" + name.toLowerCase() + "', " + expectedMinutes + ", '" + startTime + "', '" + endTime + "', '" + openedProject + "');"; 
			DatabaseAPI.createStatement(sql);
			System.out.println("Success: the activity '" + name + "' was added to the database");
		}
	}
	
	private static void seeActivities() {
		ArrayList<Integer> id = DatabaseAPI.selectInt("activities WHERE project = " + openedProject, "id");
		ArrayList<String> activityName = DatabaseAPI.selectString("activities WHERE project = " + openedProject, "activityName");
		ArrayList<Integer> expectedMinutes = DatabaseAPI.selectInt("activities WHERE project = " + openedProject, "expectedMinutes");
		ArrayList<String> startTime = DatabaseAPI.selectString("activities WHERE project = " + openedProject, "startTime");
		ArrayList<String> endTime = DatabaseAPI.selectString("activities WHERE project = " + openedProject, "endTime");
		ArrayList<Integer> nettoSpendMinutes = new ArrayList<Integer>();
		TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		
		for (int p = 0 ; p < id.size() ; p++) {
		int nettoSpendMinutesTemp = 0;
		ArrayList<Integer> allSpendMinutes = DatabaseAPI.selectInt("timeslot WHERE activity = " + id.get(p), "spendMinutes");
		for (int n = 0 ; n < allSpendMinutes.size() ; n++) {
			nettoSpendMinutesTemp = nettoSpendMinutesTemp + allSpendMinutes.get(n);
		}
		nettoSpendMinutes.add(nettoSpendMinutesTemp);
		}
		
		System.out.println("Current activities in the project:\n");
		System.out.format("%-4s %-35s %-15s %-11s %-11s %-11s  %s %n", "ID", "Name", "Allocated time", "Spend time", "Start", "End", "Allocated employees");
		for (int i = 0 ; i < activityName.size() ; i++) {
			ArrayList<String> associatedEmployees = DatabaseAPI.selectString("allocatedEmployees WHERE activity = " + id.get(i), "employee");
			String allocatedEmployees = "";
			for (int n = 0 ; n < associatedEmployees.size() ; n++) {
				allocatedEmployees = allocatedEmployees + " " + associatedEmployees.get(n);  
			}
			
			System.out.format("%-4s %-35s %-15s %-11s %-11s %-11s %s %n", id.get(i), activityName.get(i), expectedMinutes.get(i), nettoSpendMinutes.get(i), LocalDate.parse(startTime.get(i)).getYear() + "-W" + LocalDate.parse(startTime.get(i)).get(woy), LocalDate.parse(endTime.get(i)).getYear() + "-W" + LocalDate.parse(endTime.get(i)).get(woy), allocatedEmployees);
		}
	}
	
	private static void deleteActivity(String activity) {
		if (!DatabaseAPI.selectString("activities", "activityName").contains(activity)) {
			System.out.println("Error: no activity with that name exists in the database");
		} else {
			String sql = "DELETE FROM activities WHERE activityName = '" + activity + "';";
			DatabaseAPI.createStatement(sql);
			System.out.println("Success: the activity '" + activity + "' was deleted from the database");
		}
	}
	
	private static void allocateEmployee(String employeeInitials, int activityID) {
		if (!DatabaseAPI.selectString("employees", "initials").contains(employeeInitials.toUpperCase())) {
			System.out.println("Error: no employee with that name exists in the database");
		} else if (DatabaseAPI.selectString("allocatedEmployees WHERE employee = '" + employeeInitials.toUpperCase() + "' AND activity = " + activityID, "employee").size() == 1) {
			System.out.println("Error: the employee is already allocated to the activity");
		} else if (!DatabaseAPI.selectInt("activities", "id").contains(activityID)) {
			System.out.println("Error: no activity with that id exists in the database");
		} else {
			String sql = "INSERT INTO allocatedEmployees (employee, activity) " +
                    "VALUES ('" + employeeInitials.toUpperCase() + "', " + activityID + ");"; 
			DatabaseAPI.createStatement(sql);
			System.out.println("Success: the employee '" + employeeInitials.toUpperCase() + "' was allocated to activity " + activityID);
		}
	}
	
	private static void deallocateEmployee(String employeeInitials, int activityID) {
		if (!DatabaseAPI.selectString("employees", "initials").contains(employeeInitials.toUpperCase())) {
			System.out.println("Error: no employee with that name exists in the database");
		} else if (!DatabaseAPI.selectInt("activities", "id").contains(activityID)) {
			System.out.println("Error: no activity with that id exists in the database");
		} else if (!(DatabaseAPI.selectString("allocatedEmployees WHERE employee = '" + employeeInitials.toUpperCase() + "' AND activity = " + activityID, "employee").size() == 1)) {
			System.out.println("Error: the employee is not allocated to the activity");
		} else {
			String sql = "DELETE FROM allocatedEmployees WHERE employee = '" + employeeInitials.toUpperCase() + "' AND activity = " + activityID + ";";
			DatabaseAPI.createStatement(sql);
			System.out.println("Success: the employee '" + employeeInitials.toUpperCase() + "' was deallocated from activity " + activityID);
		}
	}

}






















