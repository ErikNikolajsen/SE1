Feature: Add Project
	Description: An project is added to the database of the company
	Actors: Any employee
	
Scenario: User adds a project to the database
	Given that no project with name "make cucumber tests" exist in the database
	And an employee with initials "JD" exist in the database
	When the entered project name is "make cucumber tests"
	And the entered projectleader initials are "JD"
	Then the project is added to the database

Scenario: User adds a project to the database with the projectleader being an employee that does not exist
	Given that no employee with initials "JD" exist in the database
	And that no project with name "make cucumber tests" exist in the database
	When the entered project name is "make cucumber tests"
	And the entered projectleader initials are "JD"
	Then the project is not added to the database - error one

Scenario: User adds a project that already exists in the database
	Given that a project with name "make cucumber tests" with leader "TEST" exist in the database
	And an employee with initials "JD" exist in the database
	When the entered project name is "make cucumber tests"
	And the entered projectleader initials are "JD"
	Then the project is not added to the database - error two

Scenario: User adds a project with no name
	Given an employee with initials "JD" exist in the database
	When the entered project name is ""
	And the entered projectleader initials are "JD"
	Then the project is not added to the database - error three


#--------------------------

Scenario: User deletes a project to the database
	Given that a project with name "make cucumber tests" with leader "TEST" exist in the database
	When the entered project number is equal to the project "make cucumber tests"
	Then the project is deleted from the database


#Scenario: User deletes a project that does not exist in the database
#	When the entered project number does not equal any of the projects in the database
#	Then the project is not deleted from the database




























