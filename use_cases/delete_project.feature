Feature: Delete Project
	Description: An project is deleted from the database of the company
	Actors: Any employee

Scenario: User deletes a project to the database
	Given that a project with name "make cucumber tests" with leader "TEST" exist in the database
	When the entered project number is equal to the project "make cucumber tests"
	Then the project is deleted from the database


Scenario: User deletes a project that does not exist in the database
	When the entered project number does not equal any of the projects in the database
	Then the project is not deleted from the database




























