Feature: Delete Activity
	Description: A projectleader deletes an activity from a project 
	Actors: A projectleader

Scenario: a projectleader deletes an activity from a project
	Given activity with the name "add feature files" exist in the database
	When the entered activity name is "add feature files"
	Then the activity is deleted from the database
	

Scenario: a projectleader deletes an activity that does not exist
	Given no activity with the name "add feature files" exist in the database
	When the entered activity name is "add feature files"
	Then the activity is not deleted from the database - error one
	



































