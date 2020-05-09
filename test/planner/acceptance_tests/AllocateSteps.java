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

public class AllocateSteps {
	
	public String initials;
	public int activityID;
	
	
	
	@Given("activity with the id {string} exist in the database")
	public void activityWithTheIdExistInTheDatabase(String activityID) {
		if (!DatabaseAPI.selectInt("activities", "id").contains(Integer.parseInt(activityID))) {
			DatabaseAPI.createStatement("INSERT INTO activities (id, activityName, expectedMinutes, startTime, endTime, project) VALUES (" + Integer.parseInt(activityID) + ", 'TEST ACTIVITY', 60, '2020-01-01', '2020-01-02', 7357);");
	    }
	    assertTrue(DatabaseAPI.selectInt("activities", "id").contains(Integer.parseInt(activityID)));
	}

	@Given("an the employee {string} is not allocated to the {string} activity")
	public void anTheEmployeeIsNotAllocatedToTheActivity(String initials, String activity) {
		if (DatabaseAPI.selectString("allocatedEmployees WHERE activity = " + activity, "Employee").contains(initials)) {
	    	DatabaseAPI.createStatement("DELETE FROM allocatedEmployees WHERE employee = '" + initials + "' AND activity = " + Integer.parseInt(activity) + ";");
	    }
	    assertFalse(DatabaseAPI.selectString("allocatedEmployees WHERE activity = " + activity, "Employee").contains(initials));
	}

	@When("the entered employee initials are {string}")
	public void theEnteredEmployeeInitialsAre(String string) {
	    this.initials = string;
	}

	@When("the entered activity id is {string}")
	public void theEnteredActivityIdIs(String string) {
	    this.activityID = Integer.parseInt(string);
	}

	@Then("the employee is allocated to the activity")
	public void theEmployeeIsAllocatedToTheActivity() {
		assertEquals("Success: the employee '" + initials.toUpperCase() + "' was allocated to activity " + activityID, ProjectLeader.allocateEmployee(initials, activityID));
		assertTrue(DatabaseAPI.selectString("allocatedEmployees WHERE employee = '" + initials + "' AND activity = " + activityID, "Employee").size() == 1);
	}
	
	@Then("the employee is not allocated to the activity - error one")
	public void theEmployeeIsNotAllocatedToTheActivityErrorOne() {
		assertEquals("Error: no employee with that name exists in the database", ProjectLeader.allocateEmployee(initials, activityID));
	}
	
	@Given("an the employee {string} is already allocated to the {string} activity")
	public void anTheEmployeeIsAlreadyAllocatedToTheActivity(String initials, String activity) {
		if (!DatabaseAPI.selectString("allocatedEmployees WHERE activity = " + activity, "Employee").contains(initials)) {
	    	DatabaseAPI.createStatement("INSERT INTO allocatedEmployees (employee, activity) VALUES ('" + initials + "', " + activity + ");");
	    }
	    assertTrue(DatabaseAPI.selectString("allocatedEmployees WHERE activity = " + activity + " AND employee = '" + initials + "'", "Employee").size() == 1);
	}

	@Then("the employee is not allocated to the activity - error two")
	public void theEmployeeIsNotAllocatedToTheActivityErrorTwo() {
		assertEquals("Error: the employee is already allocated to the activity", ProjectLeader.allocateEmployee(initials, activityID));
	}
	
	@Given("activity with the id {string} does not exist in the database")
	public void activityWithTheIdDoesNotExistInTheDatabase(String activityID) {
		if (DatabaseAPI.selectInt("activities", "id").contains(Integer.parseInt(activityID))) {
			DatabaseAPI.createStatement("DELETE FROM activities WHERE id = " + Integer.parseInt(activityID));
	    }
	    assertFalse(DatabaseAPI.selectInt("activities", "id").contains(Integer.parseInt(activityID)));
	}
	
	@Then("the employee is not allocated to the activity - error three")
	public void theEmployeeIsNotAllocatedToTheActivityErrorThree() {
		assertEquals("Error: no activity with that id exists in the database", ProjectLeader.allocateEmployee(initials, activityID));
	}
	
	@Then("the employee is deallocated from the activity")
	public void theEmployeeIsDeallocatedFromTheActivity() {
		assertEquals("Success: the employee '" + initials.toUpperCase() + "' was deallocated from activity " + activityID, ProjectLeader.deallocateEmployee(initials, activityID));
	}
	
	@Then("the employee is not deallocated from the activity - error one")
	public void theEmployeeIsNotDeallocatedFromTheActivityErrorOne() {
		assertEquals("Error: no employee with that name exists in the database", ProjectLeader.deallocateEmployee(initials, activityID));
	}
	
	@Then("the employee is not deallocated from the activity - error two")
	public void theEmployeeIsNotDeallocatedFromTheActivityErrorTwo() {
		assertEquals("Error: no activity with that id exists in the database", ProjectLeader.deallocateEmployee(initials, activityID));
	}
	
	@Then("the employee is not deallocated from the activity - error three")
	public void theEmployeeIsNotDeallocatedFromTheActivityErrorThree() {
		assertEquals("Error: the employee is not allocated to the activity", ProjectLeader.deallocateEmployee(initials, activityID));
	}
}

























