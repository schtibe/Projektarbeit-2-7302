package test.environment;

import junit.framework.TestCase;

import org.junit.Test;

import common.IVector;
import common.Vector;

import environment.LaneSegmentQuadratic;

public class LaneSegmentQuadraticTest extends TestCase {
	@Test 
	public void testCreation() {
		IVector vec1 = new Vector(new float[]{0,0});
		IVector vec2 = new Vector(new float[]{1,1});
		IVector vec3 = new Vector(new float[]{1,0});
		LaneSegmentQuadratic tester;
		tester = null;
		try {
			tester = new LaneSegmentQuadratic(vec1,vec2,vec3,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertNotNull(tester);
	}
	@Test (expected=IllegalArgumentException.class)
	public void testAllPointsEqual(){
		IVector vec1 = new Vector(new float[]{0,0});
		IVector vec2 = new Vector(new float[]{0,0});
		IVector vec3 = new Vector(new float[]{0,0});
		try{
			LaneSegmentQuadratic tester = new LaneSegmentQuadratic(vec1,vec2,vec3,null);
		}catch(Exception e){}
	}
	@Test (expected=IllegalArgumentException.class)
	public void testStartEqualsEndPoint(){
		IVector vec1 = new Vector(new float[]{0,0});
		IVector vec2 = new Vector(new float[]{1,1});
		IVector vec3 = new Vector(new float[]{0,0});
		try{
			LaneSegmentQuadratic tester = new LaneSegmentQuadratic(vec1,vec2,vec3,null);
		}catch(Exception e){}
	}
	@Test (expected=IllegalArgumentException.class)
	public void testBendEqualsEndPoint(){
		IVector vec1 = new Vector(new float[]{1,1});
		IVector vec2 = new Vector(new float[]{0,0});
		IVector vec3 = new Vector(new float[]{0,0});
		try{
			LaneSegmentQuadratic tester = new LaneSegmentQuadratic(vec1,vec2,vec3,null);
		}catch(Exception e){}
	}
	@Test (expected=IllegalArgumentException.class)
	public void testBendEqualsStartPoint(){
		IVector vec1 = new Vector(new float[]{1,1});
		IVector vec2 = new Vector(new float[]{1,1});
		IVector vec3 = new Vector(new float[]{0,0});
		try{
			LaneSegmentQuadratic tester = new LaneSegmentQuadratic(vec1,vec2,vec3,null);
		}catch(Exception e){}
	}
	@Test
	public void testLength() throws Exception{
		IVector vec1 = new Vector(new float[]{0,0});
		IVector vec2 = new Vector(new float[]{0,-10});
		IVector vec3 = new Vector(new float[]{10,-10});
		LaneSegmentQuadratic tester = new LaneSegmentQuadratic(vec1,vec2,vec3,null);
		assertEquals(15.7,tester.getLength(),0.8);
		
	}
	public void testPosition() throws Exception{
		IVector vec1 = new Vector(new float[]{0,0});
		IVector vec2 = new Vector(new float[]{10,10});
		IVector vec3 = new Vector(new float[]{0,10});
		LaneSegmentQuadratic tester = new LaneSegmentQuadratic(vec1,vec2,vec3,null);
		float halfLength = tester.getLength()/2;
		IVector position = tester.getVehiclePosition(halfLength);
		assertEquals(2.5,position.getComponent(0),0.05);
		assertEquals(7.5,position.getComponent(1),0.05);
	}
}
