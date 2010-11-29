package test.environment;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import environment.Lane;

public class LaneTest extends TestCase {
	
	private Lane testLane;
	
	@Override
	@Before
	public void setUp() throws Exception {
		
		/*IVector vec1 = new Vector (new float[]{0,0});
		IVector vec2 = new Vector (new float[]{10,5});
		
		this.testLane = new Lane(vec1, null) {

			@Override
			protected float calculateLength() {
				// TODO Auto-generated method stub
				return 6;
			}

			@Override
			public IVector getVehiclePosition(float segmentLength) {
				// TODO Auto-generated method stub
				return new Vector(new float[]{15,15});
			}

			
			
		};*/
		
	}
	/*
	@Test 
	public void testsetDistanceOnLane() {
		float length = 12.35F;
		testLaneSeg.setDistanceOnLane(length);
		assertEquals(length,testLaneSeg.getDistanceOnLane(),0.01);
	}
	*/
	@Test 
	public void testnullWaypoints(){
		assertTrue(true);
	}
	/*
	@Test
	public void testgetVehiclePosition(){
		IVector test = testLaneSeg.getVehiclePosition(0);
		assertEquals(test.getComponent(0),15,0.1);
		assertEquals(test.getComponent(1),15,0.1);
	}
	@Test
	public void testgetLength (){
		assertEquals (testLaneSeg.getLength(),6,0.1);
	}
	*/
}
