package planner.acceptance_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import planner.app.DatabaseAPI;
import planner.app.Projects;

public class testclass2 {

	//denne klasse laver JUnit tests p� metoden deleteProject
	@Test
	public void test() {
		
		//adds a project
		String result = Projects.addProject("JUnitTest", "TEST");
		//DatabaseAPI.createStatement(INSERT INTO projects ())
		
		//No input
		result = Projects.deleteProject("");
		assertEquals("Error: no project with that project-number exists in the database",result);
		
		//Ikke eksisterende project
		result = Projects.deleteProject("300001");
		assertEquals("Error: no project with that project-number exists in the database",result);
		
		//Too many numbers
		result = Projects.deleteProject("2000001");
		assertEquals("Error: no project with that project-number exists in the database",result);
		
		//fjerner et eksisterende projekt 
		result = Projects.deleteProject("200095");
		assertEquals("Success: the project '200095' was deleted from the database",result);
		
	}
}
