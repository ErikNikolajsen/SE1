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

#-------------------------------------------------------

#Given
#When
#Then
#And

#Scenario: User adds an employee to the database
#	Given that the name "Hubert Baumeister" are entered
#	And the initials "HB"
#	Then the administrator login succeeds
#	And the adminstrator is logged in
#
#Scenario: Administrator can login
#	Given that the administrator is not logged in
#	And the password is "adminadmin"
#	Then the administrator login succeeds
#	And the adminstrator is logged in
#	
#Scenario: Administrator has the wrong password
#	Given that the administrator is not logged in
#	And the password is "wrong password"
#	Then the administrator login fails
#	And the administrator is not logged in
#
#
#
#    
#Scenario: Create employee successfully
#	Given employee with initials "JD" exists
#    And the employee has the name "John Doe"
#    When I check for the employee is created
#    Then I get the name "John Doe" and the initials "JD"
#    And the initials "JD" is in the initials list
#    And the employeelist conatins the employee
#
#
#Scenario: Fail create employees with duplicate initials
#	Given employee with initials "JD" exists
#	When I add another employee with the initials "JD"
#	Then I get the error message "An employee with the same initials is already in the system" 