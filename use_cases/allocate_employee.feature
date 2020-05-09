Feature: Allocate Employee
	Description: A projectleader allocates an employee to an activity
	Actors: A projectleader

Scenario: a leader allocates an employee to an activity
	Given activity with the id "1234" exist in the database
	And an employee with initials "JD" exist in the database
	And an the employee "JD" is not allocated to the "1234" activity
	When the entered employee initials are "JD"
	And the entered activity id is "1234"
	Then the employee is allocated to the activity

Scenario: a leader allocates an employee that does not exist to an activity
	Given activity with the id "1234" exist in the database
	And an the employee "JD" is not allocated to the "1234" activity
	And that no employee with initials "JD" exist in the database
	When the entered employee initials are "JD"
	And the entered activity id is "1234"
	Then the employee is not allocated to the activity - error one

Scenario: a leader allocates an employee to an activity that the employee is already allocated to
	Given activity with the id "1234" exist in the database
	And an employee with initials "JD" exist in the database
	And an the employee "JD" is already allocated to the "1234" activity
	When the entered employee initials are "JD"
	And the entered activity id is "1234"
	Then the employee is not allocated to the activity - error two

Scenario: a leader allocates an employee to an activity that does not exist
	Given activity with the id "1234" does not exist in the database
	And an employee with initials "JD" exist in the database
	And an the employee "JD" is not allocated to the "1234" activity
	When the entered employee initials are "JD"
	And the entered activity id is "1234"
	Then the employee is not allocated to the activity - error three
































