Feature: Add Activity
	Description: A projectleader adds an activity to a project 
	Actors: A projectleader

Scenario: a projectleader adds an activity to a project
	Given that a project with name "make cucumber tests" with leader "TEST" exist in the database
	And the opened project is "make cucumber tests"
	And no activity with the name "add feature files" exist in the database
	When the entered activity name is "add feature files"
	And the entered expected time is "60"
	And the entered start year is "2020"
	And the entered start week is "16"
	And the entered end year is "2020"
	And the entered end week is "17"
	Then the activity is added to the database
	

Scenario: a leader adds an activity with an end-date that is before the start-date
	Given that a project with name "make cucumber tests" with leader "TEST" exist in the database
	And the opened project is "make cucumber tests"
	When the entered activity name is "add feature files"
	And the entered expected time is "60"
	And the entered start year is "2020"
	And the entered start week is "16"
	And the entered end year is "2020"
	And the entered end week is "15"
	Then the activity is not added to the database - error one

Scenario: a leader adds an activity already in the database  
	Given that a project with name "make cucumber tests" with leader "TEST" exist in the database
	And the opened project is "make cucumber tests"
	And activity with the name "add feature files" exist in the database
	When the entered activity name is "add feature files"
	And the entered expected time is "60"
	And the entered start year is "2020"
	And the entered start week is "16"
	And the entered end year is "2020"
	And the entered end week is "17"
	Then the activity is not added to the database - error two

Scenario: a leader adds an activity with no name
	Given that a project with name "make cucumber tests" with leader "TEST" exist in the database
	And the opened project is "make cucumber tests"
	And no activity with the name "add feature files" exist in the database
	When the entered activity name is ""
	And the entered expected time is "60"
	And the entered start year is "2020"
	And the entered start week is "16"
	And the entered end year is "2020"
	And the entered end week is "17"
	Then the activity is added to the database - error three

	



































