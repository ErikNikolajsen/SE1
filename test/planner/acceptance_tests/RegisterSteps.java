package planner.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import planner.app.DatabaseAPI;
import planner.app.Employees;
import planner.app.Model;
import planner.app.ProjectLeader;
import planner.app.Projects;
import planner.app.Activity;

public class RegisterSteps {
	
	public int activityID;
	public int spendTime;
	public String currentUser;
	
	@Given("activity with the id {string} with startdate {string} and enddate {string} and a time budget {string} exist in the database")
	public void activityWithTheIdWithStartdateAndEnddateAndATimeBudgetExistInTheDatabase(String id, String startdate, String enddate, String timebudget) {
		if (!DatabaseAPI.selectInt("activities", "id").contains(Integer.parseInt(id))) {
			DatabaseAPI.createStatement("INSERT INTO activities (id, activityName, expectedMinutes, startTime, endTime, project) VALUES (" + Integer.parseInt(id) + ", 'TEST ACTIVITY', "+Integer.parseInt(timebudget)+", '"+startdate+"', '"+enddate+"', 7357);");
		}
	    assertTrue(DatabaseAPI.selectInt("activities", "id").contains(Integer.parseInt(id)));
	}

	@Given("the current day is {string}")
	public void theCurrentDayIs(String string) {
	    Activity.currentDay = LocalDate.parse(string);
	}

	@When("the entered activity is {string}")
	public void theEnteredActivityIs(String string) {
	    this.activityID = Integer.parseInt(string);
	}

	@When("the entered spend time is {string}")
	public void theEnteredSpendTimeIs(String string) {
	   this.spendTime = Integer.parseInt(string);
	}

	@Then("the registered hours are added to the database")
	public void theRegisteredHoursAreAddedToTheDatabase() {
		assertEquals("Success: the timeslot was successfully was added to the database", Activity.registerHours(activityID, spendTime));
		assertTrue(DatabaseAPI.selectInt("timeslot WHERE employee = '" + currentUser + "' AND activity = " + activityID, "id").size() >= 1);
	}
	
	@Given("the employee {string} is logged in currently")
	public void theEmployeeIsLoggedInCurrently(String string) {
	    this.currentUser = string;
	}
	
	@Then("the registered hours are not added to the database - error one")
	public void theRegisteredHoursAreNotAddedToTheDatabaseErrorOne() {
		assertEquals("Error: activity does not exist in the database", Activity.registerHours(activityID, spendTime));
	}
	
	@Given("no hours are already registered to the {string} activity")
	public void noHoursAreAlreadyRegisteredToTheActivity(String string) {
		if (DatabaseAPI.selectInt("timeslot", "activity").contains(Integer.parseInt(string))) {
	    	DatabaseAPI.createStatement("DELETE FROM timeslot WHERE activity = '" + Integer.parseInt(string) + "';");
	    }
	    assertFalse(DatabaseAPI.selectInt("timeslot", "activity").contains(Integer.parseInt(string)));
	}
	
	@Then("the registered hours are not added to the database - error two")
	public void theRegisteredHoursAreNotAddedToTheDatabaseErrorTwo() {
		assertEquals("Error: the activity has not yet started or it has passed", Activity.registerHours(activityID, spendTime));
	}
	
	@Then("the registered hours are not added to the database - error three")
	public void theRegisteredHoursAreNotAddedToTheDatabaseErrorThree() {
		assertEquals("Error: total spend minutes exceeds allowed amount", Activity.registerHours(activityID, spendTime));
	}
	
	@Then("the registered hours are not added to the database - error four")
	public void theRegisteredHoursAreNotAddedToTheDatabaseErrorFour() {
		assertEquals("Error: you are not allocated to the activity", Activity.registerHours(activityID, spendTime));
	}
	
	@When("{string} minutes have already been registered as spent on the activity")
	public void minutesHaveAlreadyBeenRegisteredAsSpentOnTheActivity(String string) {
		DatabaseAPI.createStatement("INSERT INTO timeslot (employee, activity, spendMinutes, day) VALUES ('"+currentUser+"', "+activityID+", "+string+", '"+Activity.currentDay+"');");
	}
}



























