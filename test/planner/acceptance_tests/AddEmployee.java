package planner.acceptance_tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import planner.app.DatabaseAPI;
import planner.app.Employees;
import planner.app.Model;

public class AddEmployee {
	
	private String name;
	private String initials;
	
	@Given("that no employee with initials {string} exist in the database")
	public void thatNoEmployeeWithInitialsExistInTheDatabase(String initials2) {
	    if (DatabaseAPI.selectString("employees", "initials").contains(initials2)) {
	    	Employees.deleteEmployee(initials2);
	    }
	    
	    System.out.println("1 "+DatabaseAPI.selectString("employees", "initials").contains("jd".toUpperCase())); //temp
	}
	
	@When("the entered name is {string}")
	public void theEnteredNameIs(String password) throws Exception {
		this.name = password;
	}
	
	@When("the entered initials are {string}")
	public void theEnteredInitialsAre(String password) throws Exception {
		this.initials = password;
	}
	
	@Then("the employee is added to the database")
	public void theEmployeeIsAddedToTheDatabase() throws Exception {
		System.out.println(DatabaseAPI.selectString("employees", "initials").contains("jd".toUpperCase())); //temp
		assertEquals("Success: the employee " + initials.toUpperCase() + " was added to the database", Employees.addEmployee(name, initials));
		assertTrue(DatabaseAPI.selectString("employees", "initials").contains(initials.toUpperCase()));
	}
	
	@Given("an employee with initials {string} exist in the database")
	public void anEmployeeWithInitialsExistInTheDatabase(String initials2) {
		if (!DatabaseAPI.selectString("employees", "initials").contains(initials2)) {
			DatabaseAPI.createStatement("INSERT INTO employees (initials, name) VALUES ('" + initials2 + "', 'John Doe');");
		}
	}

	@Then("the new employee is not added to the database - error one")
	public void theNewEmployeeIsNotAddedToTheDatabaseErrorOne() {
		assertEquals(Employees.addEmployee(name, initials), "Error: initials already exists in the database");
	}
	
	@Then("the new employee is not added to the database - error two")
	public void theNewEmployeeIsNotAddedToTheDatabaseErrorTwo() {
		assertEquals(Employees.addEmployee(name, initials), "Error: initials must be less than 5 characters long");
	}
	
	@Then("the new employee is not added to the database - error three")
	public void theNewEmployeeIsNotAddedToTheDatabaseErrorThree() {
		assertEquals(Employees.addEmployee(name, initials), "Error: empty name string");
	}
	
	@Then("the new employee is not added to the database - error four")
	public void theNewEmployeeIsNotAddedToTheDatabaseErrorFour() {
		assertEquals(Employees.addEmployee(name, initials), "Error: empty initials string");
	}
	
	//--------------------------
	
	@Then("the employee is deleted from the database")
	public void theEmployeeIsDeletedFromTheDatabase() {
		assertEquals(Employees.deleteEmployee(initials), "Success: the employee " + initials.toUpperCase() + " was deleted from the database");
		assertFalse(DatabaseAPI.selectString("employees", "initials").contains(initials));
	}
	
	@Then("the employee is not deleted from the database - error one")
	public void theEmployeeIsNotDeletedFromTheDatabaseErrorOne() {
		assertEquals(Employees.deleteEmployee(initials), "Error: no employee with those initials exists in the database");
	}
	
	@Given("the employee {string} is currently logged in")
	public void theEmployeeIsCurrentlyLoggedIn(String initials2) {
		Model.currentUser = initials2;
	}

	@Then("the employee is not deleted from the database - error two")
	public void theEmployeeIsNotDeletedFromTheDatabaseErrorTwo() {
		assertEquals(Employees.deleteEmployee(initials), "Error: it is not possible to delete yourself");
	}
	
	@Given("the employee {string} is a project leader")
	public void theEmployeeIsAProjectLeader(String string) {
	    if (!(DatabaseAPI.selectString("projects WHERE projectLeader = '" + initials.toUpperCase() + "'", "projectNumber").size() >= 1)) {
	    	
	    }
	}

	@Then("the employee is not deleted from the database - error three")
	public void theEmployeeIsNotDeletedFromTheDatabaseErrorThree() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
}
































