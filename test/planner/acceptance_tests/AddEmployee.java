package planner.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import planner.app.DatabaseAPI;
import planner.app.Employees;

public class AddEmployee {
	
	private Employees employees;
	private String name;
	private String initials;
	
	@Given("the entered name is {string}")
	public void theEnteredNameIs(String password) throws Exception {
		this.name = password;
	}
	
	@Given("the entered initials are {string}")
	public void theEnteredInitialsAre(String password) throws Exception {
		this.initials = password;
	}
	
	@Then("the employee is added to the database")
	public void theEmployeeIsAddedToTheDatabase() throws Exception {
		assertEquals(employees.addEmployee(name, initials), "Success: the employee " + initials.toUpperCase() + " was added to the database");
		assertTrue(DatabaseAPI.selectString("employees", "initials").contains(initials.toUpperCase()));
		employees.deleteEmployee(initials);
	}
	
	@Given("an employee with initials {string} exist in the database")
	public void anEmployeeWithInitialsExistInTheDatabase(String initials) {
		DatabaseAPI.createStatement("INSERT INTO employees (initials, name) VALUES ('" + initials + "', 'John Doe');");
		assertTrue(DatabaseAPI.selectString("employees", "initials").contains(initials));
	}

	@Then("the new employee is not added to the database - error one")
	public void theNewEmployeeIsNotAddedToTheDatabaseErrorOne() {
		assertEquals(employees.addEmployee(name, initials), "Error: initials already exists in the database");
		employees.deleteEmployee(initials);
	}
	
	@Then("the new employee is not added to the database - error two")
	public void theNewEmployeeIsNotAddedToTheDatabaseErrorTwo() {
		assertEquals(employees.addEmployee(name, initials), "Error: initials must be less than 5 characters long");
	}
	
	@Then("the new employee is not added to the database - error three")
	public void theNewEmployeeIsNotAddedToTheDatabaseErrorThree() {
		assertEquals(employees.addEmployee(name, initials), "Error: empty name string");
	}
	
	@Then("the new employee is not added to the database - error four")
	public void theNewEmployeeIsNotAddedToTheDatabaseErrorFour() {
		assertEquals(employees.addEmployee(name, initials), "Error: empty initials string");
	}
}
































