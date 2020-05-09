Feature: Deallocate Employee
	Description: A projectleader deallocates an employee from an activity
	Actors: A projectleader

Scenario: a leader deallocates an employee from an activity
	Given activity with the id "1234" exist in the database
	And an employee with initials "JD" exist in the database
	And an the employee "JD" is already allocated to the "1234" activity
	When the entered employee initials are "JD"
	And the entered activity id is "1234"
	Then the employee is deallocated from the activity
	
Scenario: a leader deallocates an employee that does not exist from an activity
	Given activity with the id "1234" exist in the database
	And that no employee with initials "JD" exist in the database
	And an the employee "JD" is already allocated to the "1234" activity
	When the entered employee initials are "JD"
	And the entered activity id is "1234"
	Then the employee is not deallocated from the activity - error one

Scenario: a leader deallocates an employee from an activity that does not exist
	Given activity with the id "1234" does not exist in the database
	And an employee with initials "JD" exist in the database
	And an the employee "JD" is already allocated to the "1234" activity
	When the entered employee initials are "JD"
	And the entered activity id is "1234"
	Then the employee is not deallocated from the activity - error two

Scenario: a leader deallocates an employee from an activity that the employee is not allocated to
	Given activity with the id "1234" exist in the database
	And an employee with initials "JD" exist in the database
	And an the employee "JD" is not allocated to the "1234" activity
	When the entered employee initials are "JD"
	And the entered activity id is "1234"
	Then the employee is not deallocated from the activity - error three
































