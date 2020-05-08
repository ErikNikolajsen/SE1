Feature: Delete Employee
	Description: An employee is deleted from the database of the company
	Actors: Any employee except the logged in user deleting himself

Scenario: User deletes an employee from the database
	Given an employee with initials "JD" exist in the database
	And the employee "JD" is not currently logged in
	When the entered initials are "JD"
	Then the employee is deleted from the database

Scenario: User deletes an employee that does not exist in the database
	Given that no employee with initials "JD" exist in the database
	When the entered initials are "JD"
	Then the employee is not deleted from the database - error one

Scenario: User deletes himself from the database
	Given an employee with initials "JD" exist in the database
	And the employee "JD" is currently logged in
	When the entered initials are "JD"
	Then the employee is not deleted from the database - error two
	





































