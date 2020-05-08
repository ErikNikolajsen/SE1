Feature: Add Employee
	Description: An employee is added to the database of the company
	Actors: Any employee
	
Scenario: User adds an employee to the database
	When the entered name is "John Doe"
	And the entered initials are "JD"
	Then the employee is added to the database

Scenario: User adds an employee with initials that already exists in the database
	Given an employee with initials "JD" exist in the database
	When the entered name is "Johnson Doe"
	And the entered initials are "JD"
	Then the new employee is not added to the database - error one

Scenario: User adds an employee with initials that are more than four characters long
	When the entered name is "John Smith Williams Brown Doe"
	And the entered initials are "JSWBD"
	Then the new employee is not added to the database - error two

Scenario: User adds an employee with no name
	When the entered name is ""
	And the entered initials are "JD"
	Then the new employee is not added to the database - error three

Scenario: User adds an employee with no initials
	When the entered name is "John Doe"
	And the entered initials are ""
	Then the new employee is not added to the database - error four