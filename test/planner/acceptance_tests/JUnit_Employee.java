/*///////////////////////
 * 
 *    Author:
 *    Joachim Leick Espersen
 *    s194287
 * 
 *///////////////////////

package planner.acceptance_tests;

import static org.junit.Assert.*;
import org.junit.Test;
import planner.app.Employees;

public class JUnit_Employee {
// Denne klasse laver JUnit tests p� addEmployee metoden

	@Test
	public void test1() {
		
		//no inputs
		String result = Employees.addEmployee("", "");
		assertEquals("Error: empty name string",result);
		
		//only name as input
		result = Employees.addEmployee("Joachim Leick Espersen", "");
		assertEquals("Error: empty initials string",result);
		
		//only initials as input
		result = Employees.addEmployee("", "JLEP");
		assertEquals("Error: empty name string", result);
		
		//Both name and initials as input
		result = Employees.addEmployee("Joachim Leick Espersen", "JLEP");
		assertEquals("Success: the employee JLEP was added to the database",result);
		//Deletes the added user
		result = Employees.deleteEmployee("JLEP"); 
		assertEquals("Success: the employee JLEP was deleted from the database",result);
		
		//More than 4 initials
		result = Employees.addEmployee("Joachim Leick Espersen", "JLESP");
		assertEquals("Error: initials must be less than 5 characters long", result);
		
		//Less than 4 initials
		result = Employees.addEmployee("Joachim Lecik Espersen", "JLE");
		assertEquals("Success: the employee JLE was added to the database",result);
		
		//Name already exits
		result = Employees.addEmployee("Joachim Leick Espersen", "JLE");
		assertEquals("Error: initials already exists in the database",result);
		//deletes the added user
		result = Employees.deleteEmployee("JLE");
		assertEquals("Success: the employee JLE was deleted from the database",result);
	}
}
