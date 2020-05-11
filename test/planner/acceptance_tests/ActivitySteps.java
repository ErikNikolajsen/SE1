/*///////////////////////
 * 
 *    Author:
 *    Erik Ravn Nikolajsen
 *    s144382
 * 
 *///////////////////////

package planner.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import planner.app.DatabaseAPI;
import planner.app.Employees;
import planner.app.Model;
import planner.app.ProjectLeader;
import planner.app.Projects;

public class ActivitySteps {
	
	public String activityName;
	public int expectedTime;
	public int startYear;
	public int startWeek;
	public int endYear;
	public int endWeek;
	
	@Given("the opened project is {string}")
	public void theOpenedProjectIs(String string) {
	    ProjectLeader.openedProject = string;
	}
	
	@Given("no activity with the name {string} exist in the database")
	public void noActivityWithTheNameExistInTheDatabase(String name) {
		if (DatabaseAPI.selectString("activities", "activityName").contains(name)) {
	    	DatabaseAPI.createStatement("DELETE FROM activities WHERE activityName = '" + name + "';");
	    }
	    assertFalse(DatabaseAPI.selectString("activities", "activityName").contains(name));
	}
	
	@When("the entered activity name is {string}")
	public void theEnteredActivityNameIs(String string) {
	    this.activityName = string;
	}

	@When("the entered expected time is {string}")
	public void theEnteredExpectedTimeIs(String string) {
	    this.expectedTime = Integer.parseInt(string);
	}

	@When("the entered start year is {string}")
	public void theEnteredStartYearIs(String string) {
		this.startYear = Integer.parseInt(string);
	}

	@When("the entered start week is {string}")
	public void theEnteredStartWeekIs(String string) {
		this.startWeek = Integer.parseInt(string);
	}

	@When("the entered end year is {string}")
	public void theEnteredEndYearIs(String string) {
		this.endYear = Integer.parseInt(string);
	}

	@When("the entered end week is {string}")
	public void theEnteredEndWeekIs(String string) {
		this.endWeek = Integer.parseInt(string);
	}

	@Then("the activity is added to the database")
	public void theActivityIsAddedToTheDatabase() {
		assertEquals("Success: the activity '" + activityName + "' was added to the database", ProjectLeader.addActivity(activityName, expectedTime, startYear, startWeek, endYear, endWeek));
		assertTrue(DatabaseAPI.selectString("activities", "activityName").contains(activityName));
	}
	
	@Then("the activity is not added to the database - error one")
	public void theActivityIsNotAddedToTheDatabaseErrorOne() {
		assertEquals("Error: activity start-date is after the end-date", ProjectLeader.addActivity(activityName, expectedTime, startYear, startWeek, endYear, endWeek));
	}
	
	@Given("activity with the name {string} exist in the database")
	public void activityWithTheNameExistInTheDatabase(String string) {
		if (!DatabaseAPI.selectString("activities", "activityName").contains(string)) {
			ProjectLeader.addActivity(string, 120, 2020, 49, 2021, 3);
	    }
	    assertTrue(DatabaseAPI.selectString("activities", "activityName").contains(string));
	}

	@Then("the activity is not added to the database - error two")
	public void theActivityIsNotAddedToTheDatabaseErrorTwo() {
		assertEquals("Error: activity name already exists in the database", ProjectLeader.addActivity(activityName, expectedTime, startYear, startWeek, endYear, endWeek));
	}
	
	@Then("the activity is added to the database - error three")
	public void theActivityIsAddedToTheDatabaseErrorThree() {
		assertEquals("Error: empty name string", ProjectLeader.addActivity(activityName, expectedTime, startYear, startWeek, endYear, endWeek));
	}
	
	@Then("the activity is deleted from the database")
	public void theActivityIsDeletedFromTheDatabase() {
		assertEquals("Success: the activity '" + activityName + "' was deleted from the database", ProjectLeader.deleteActivity(activityName));
		assertFalse(DatabaseAPI.selectString("activities", "activityName").contains(activityName));
	}
	
	@Then("the activity is not deleted from the database - error one")
	public void theActivityIsNotDeletedFromTheDatabaseErrorOne() {
		assertEquals("Error: no activity with that name exists in the database", ProjectLeader.deleteActivity(activityName));
	}
	
}

























