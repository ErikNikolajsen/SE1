Feature: Register Hours
	Description: An employee registers time spend on an activity that is assigned to him 
	Actors: Any employee assigned to an activity

Scenario: User registers hours successfully
	Given activity with the id "920" with startdate "2020-01-01" and enddate "2020-02-01" and a time budget "180" exist in the database
	And the current day is "2020-01-17"
	And the employee "JD" is logged in currently
	And an the employee "JD" is already allocated to the "920" activity
	And no hours are already registered to the "920" activity
	When the entered activity is "920"
	And the entered spend time is "60"
	Then the registered hours are added to the database

Scenario: User registers hours to a non-existing activity
	Given activity with the id "920" does not exist in the database
	And the current day is "2020-01-17"
	And the employee "JD" is logged in currently
	And an the employee "JD" is already allocated to the "920" activity
	When the entered activity is "920"
	And the entered spend time is "60"
	Then the registered hours are not added to the database - error one

Scenario: User registers hours outside of the activity time-period
	Given activity with the id "920" with startdate "2020-01-01" and enddate "2020-02-01" and a time budget "180" exist in the database
	And the current day is "2020-03-14"
	And the employee "JD" is logged in currently
	And an the employee "JD" is already allocated to the "920" activity
	And no hours are already registered to the "920" activity
	When the entered activity is "920"
	And the entered spend time is "60"
	Then the registered hours are not added to the database - error two

Scenario: User registers more hours than the activity is budgeted for
	Given activity with the id "920" with startdate "2020-01-01" and enddate "2020-02-01" and a time budget "180" exist in the database
	And the current day is "2020-01-17"
	And the employee "JD" is logged in currently
	And an the employee "JD" is already allocated to the "920" activity
	And no hours are already registered to the "920" activity
	When the entered activity is "920"
	And the entered spend time is "200"
	Then the registered hours are not added to the database - error three

Scenario: User registers hours to an activity he is not allocated to
	Given activity with the id "920" with startdate "2020-01-01" and enddate "2020-02-01" and a time budget "180" exist in the database
	And the current day is "2020-01-17"
	And the employee "JD" is logged in currently
	And an the employee "JD" is not allocated to the "920" activity
	And no hours are already registered to the "920" activity
	When the entered activity is "920"
	And the entered spend time is "60"
	Then the registered hours are not added to the database - error four



































