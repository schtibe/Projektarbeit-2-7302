package test.environment;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import common.IVector;
import common.Vector;

import environment.LaneSegment;
import environment.LaneSegmentLinear;
import environment.SignWayPoint;

public class LaneSegmentTest extends TestCase {
	
	private LaneSegment<LaneSegmentLinear> testLaneSeg;
	
	@Override
	@Before
	public void setUp() throws Exception {
		
		IVector vec1 = new Vector (new float[]{0,0});
		IVector vec2 = new Vector (new float[]{10,5});
		
		this.testLaneSeg = new LaneSegment<LaneSegmentLinear>(vec1, vec2, null) {

			@Override
			protected float calculateLength() {
				
				return 6;
			}

			@Override
			public IVector getVehiclePosition(float segmentLength) {
				
				return new Vector(new float[]{15,15});
			}

			@Override
			public IVector[] getBezierPoints() {
				
				return null;
			}

			
			
		};
		
	}
	
	@Test 
	public void testsetDistanceOnLane() {
		float length = 12.35F;
		testLaneSeg.setDistanceOnLane(length);
		assertEquals(length,testLaneSeg.getDistanceOnLane(),0.01);
	}
	@Test 
	public void testnullWaypoints(){
		assertEquals(new ArrayList<SignWayPoint> (),testLaneSeg.getSignWayPoints(0));
	}
	@Test
	public void testgetVehiclePosition(){
		IVector test = testLaneSeg.getVehiclePosition(0);
		assertEquals(test.getComponent(0),15,0.1);
		assertEquals(test.getComponent(1),15,0.1);
	}
}
