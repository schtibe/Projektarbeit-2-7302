package test.environment;

import junit.framework.TestCase;

import org.junit.Test;

import common.IVector;
import common.Vector;

import environment.LaneSegmentLinear;

public class LaneSegmentLinearTest extends TestCase {
	@Test 
	public void testCreation() {
		IVector vec1 = new Vector(new float[]{0,0});
		IVector vec2 = new Vector(new float[]{1,1});
		LaneSegmentLinear tester;
		tester = null;
		try {
			tester = new LaneSegmentLinear(vec1,vec2,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(tester);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testAllPointsEqual(){
		IVector vec1 = new Vector(new float[]{0,0});
		IVector vec2 = new Vector(new float[]{0,0});
		try{
			LaneSegmentLinear tester = new LaneSegmentLinear(vec1,vec2,null);
		}catch(Exception e){}
	}
	@Test
	public void testLength() throws Exception{
		IVector vec1 = new Vector(new float[]{0,0});
		IVector vec2 = new Vector(new float[]{10,10});
		LaneSegmentLinear tester = new LaneSegmentLinear(vec1,vec2,null);
		assertEquals(14.1,tester.getLength(),0.1);
		
	}
	public void testPosition() throws Exception{
		IVector vec1 = new Vector(new float[]{0,0});
		IVector vec2 = new Vector(new float[]{10,10});
		LaneSegmentLinear tester = new LaneSegmentLinear(vec1,vec2,null);
		float halfLength = tester.getLength()/2;
		IVector position = tester.getVehiclePosition(halfLength);
		assertEquals(5.0,position.getComponent(0),0.05);
		assertEquals(5.0,position.getComponent(1),0.05);
	}
}
