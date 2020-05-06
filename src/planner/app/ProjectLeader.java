package planner.app;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProjectLeader {
	
	private static ArrayList<String> usersProjects;
	private static String openedProject;
	
	public static void chooseProject() {
		usersProjects = SQLiteJDBC.selectString("projects WHERE projectLeader = '" + Model.currentUser + "'", "projectNumber");
		openedProject = null;
		
		if (usersProjects.size() == 1) {
			openedProject = usersProjects.get(0);
			displayProjectLeader();
		} else {
			System.out.println("Choose project name:");
			for (int i = 0 ; i < usersProjects.size() ; i++) {
				System.out.println(usersProjects.get(i));
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
		System.out.println("Project: " + openedProject);
		System.out.println("Project Leader Menu");
		System.out.println("Choose menu item:");
		System.out.println("1. See Activities");
		System.out.println("2. Add Activity");
		System.out.println("3. Delete Activity");
		System.out.println("4. See Daily Journal");
		System.out.println("5. Exit");
		
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
				ProjectLeader.displayJournalView();
			} else if (n.equals("5")) {
				validInput = true;
				Menu.displayMenu();
			} else {
				System.out.println("Error: invalid input");
			}
		}
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
		} else if (SQLiteJDBC.selectString("activities", "activityName").contains(name.toLowerCase())) {
			System.out.println("Error: activity name already exists in the database");
			
		// If no errors are found in the inserted data the employee is added to the database as a non-projectLeader
		} else {
			String sql = "INSERT INTO activities (activityName, expectedMinutes, startTime, endTime, project) " +
                      "VALUES ('" + name.toLowerCase() + "', " + expectedMinutes + ", '" + startTime + "', '" + endTime + "', '" + openedProject + "');"; 
			SQLiteJDBC.createStatement(sql);
			System.out.println("Success: the activity '" + name + "' was added to the database");
		}
		
	}
	
	private static void seeActivities() {
		ArrayList<String> activityName = SQLiteJDBC.selectString("activities WHERE project = " + openedProject, "activityName");
		ArrayList<Integer> expectedMinutes = SQLiteJDBC.selectInt("activities WHERE project = " + openedProject, "expectedMinutes");
		ArrayList<String> startTime = SQLiteJDBC.selectString("activities WHERE project = " + openedProject, "startTime");
		ArrayList<String> endTime = SQLiteJDBC.selectString("activities WHERE project = " + openedProject, "endTime");
		System.out.println("Current activities in the project:\n");
		System.out.format("%-35s %-12s %-11s %-11s %n", "Name", "Minutes", "Start", "End");
		for (int i = 0 ; i < activityName.size() ; i++) {
			System.out.format("%-35s %-12s %-11s %-11s %n",activityName.get(i), expectedMinutes.get(i), startTime.get(i), endTime.get(i));
		}
		
	}

}
























