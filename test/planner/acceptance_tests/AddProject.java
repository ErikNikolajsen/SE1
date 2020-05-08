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
import planner.app.Projects;

public class AddProject {
	
	private String projectName;
	private String leaderInitials;
	private int projectNumber;
	
	// Add Project
	
	@Given("that no project with name {string} exist in the database")
	public void thatNoProjectWithNameExistInTheDatabase(String name) {
	    if (DatabaseAPI.selectString("projects", "projectName").contains(name)) {
	    	DatabaseAPI.createStatement("DELETE FROM projects WHERE projectName = '" + name + "';");
	    }
	    assertFalse(DatabaseAPI.selectString("projects", "projectName").contains(name));
	}

	@When("the entered project name is {string}")
	public void theEnteredProjectNameIs(String name) {
		this.projectName = name.toLowerCase();
	}

	@When("the entered projectleader initials are {string}")
	public void theEnteredProjectleaderInitialsAre(String initials) {
		this.leaderInitials = initials.toUpperCase();
	}

	@Then("the project is added to the database")
	public void theProjectIsAddedToTheDatabase() {
	    assertEquals("Success: the project '" + projectName + "' was added to the database", Projects.addProject(projectName, leaderInitials));
	    assertTrue(DatabaseAPI.selectString("projects", "projectName").contains(projectName));
	}
	
	@Then("the project is not added to the database - error one")
	public void theProjectIsNotAddedToTheDatabaseErrorOne() {
		assertEquals("Error: invalid employeer initials", Projects.addProject(projectName, leaderInitials));
	}
	
	@Given("that a project with name {string} with leader {string} exist in the database")
	public void thatAProjectWithNameWithLeaderExistInTheDatabase(String project, String initials) {
		if (!DatabaseAPI.selectString("employees", "initials").contains(initials)) {
			System.out.println(Employees.addEmployee("Test Employee", initials));
	    }
		
		if (!DatabaseAPI.selectString("projects", "projectName").contains(project)) {
			System.out.println(Projects.addProject(project, initials));
	    }
	    assertTrue(DatabaseAPI.selectString("projects", "projectName").contains(project));
	}

	@Then("the project is not added to the database - error two")
	public void theProjectIsNotAddedToTheDatabaseErrorTwo() {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals("Error: project already exists in the database", Projects.addProject(projectName, leaderInitials));
	}
	
	@Then("the project is not added to the database - error three")
	public void theProjectIsNotAddedToTheDatabaseErrorThree() {
		assertEquals("Error: empty name string", Projects.addProject(projectName, leaderInitials));
	}


//--------------------------
	
	// Delete project

	@When("the entered project number is equal to the project {string}")
	public void theEnteredProjectNumberIsEqualToTheProject(String projectName) {
	    this.projectNumber = DatabaseAPI.selectInt("projects WHERE projectName = '" + projectName + "'", "projectNumber").get(0);
	}
	
	@Then("the project is deleted from the database")
	public void theProjectIsDeletedFromTheDatabase() {
		assertEquals("Success: the project '" + projectNumber + "' was deleted from the database", Projects.deleteProject(Integer.toString(projectNumber)));
	}


}

























