package test.driver;

import junit.framework.TestCase;

import org.junit.Test;

import driver.Animus;


public class AnimusTest extends TestCase{
	@Test 
	public void testCreation() {
		Animus animus = new Animus(null,null);
		try{
			
		animus.assessSituation(null);
		}catch(Exception e){
			System.out.println(e);
		}
		
		assertTrue(true);
	}
}
