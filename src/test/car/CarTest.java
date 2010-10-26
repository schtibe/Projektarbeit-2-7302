package test.car;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import car.Car;
import car.CarCannotReverseException;

import common.GlobalConstants;
import common.IVector;
import common.Vector;

import environment.IJunction;
import environment.ILane;
import environment.ILaneSegment;
import environment.IWayPoint;

public class CarTest {
	
	private Car car;
	private ILane lane;
	private IVector carPosition;

	@Before
	public void setUp() throws Exception {
		this.carPosition = new Vector(new float[]{2,3});
		this.lane = new ILane() {

			@Override
			public IVector getVehiclePosition(float drivenDistance) {
				return new Vector(
						new float[]{
								carPosition.getComponent(0),
								carPosition.getComponent(1)
								}
						);
			}

			@Override
			public List<IWayPoint> getWayPointsAtPosition(float lanePosition,
					float viewField) {

				// probably should return some way points
				return new ArrayList<IWayPoint>();
			}

			@Override
			public ILaneSegment<?> getFirstILaneSegment() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IJunction getJunction() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<IVector[]> getLanePath() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IVector getStartPosition() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setJunction(IJunction junction) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public ILaneSegment<?> getLastILaneSegment() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public float getLength() {
				// TODO Auto-generated method stub
				return 0;
			}		
		};
		
		this.car = new Car(this.lane, 2);
	}
	
	@Test
	public void testCar() {
		assertEquals(this.lane, this.car.getLane());
		assertEquals(this.carPosition.getComponent(0), 
				this.car.getPosition().getComponent(0), 0.001);
		assertEquals(this.carPosition.getComponent(1),
				this.car.getPosition().getComponent(1), 0.001);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testCarException() {
		Car car = new Car(null);
	}

	@Test
	public void testGetSpeed() {
		assertEquals(0, this.car.getSpeed(), 0);
	}

	@Test
	public void testChangeLange() {
		ILane newlane = new ILane() {
			@Override
			public IVector getVehiclePosition(float drivenDistance) {
				return new Vector(new float[]{
						carPosition.getComponent(0),
						carPosition.getComponent(1)}
				);
			}

			@Override
			public List<IWayPoint> getWayPointsAtPosition(float lanePosition,
					float viewField) {

				// probably should return some way points
				return new ArrayList<IWayPoint>();
			}

			@Override
			public ILaneSegment<?> getFirstILaneSegment() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IJunction getJunction() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<IVector[]> getLanePath() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IVector getStartPosition() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setJunction(IJunction junction) {
				// TODO Auto-generated method stub
				
			}	
		};
		
		// drive the car a bit to see if the drivenLaneDistance is set back to 0
		this.car.accelerate(20);
		for (int i = 0; i < 10; i++) {
			try {
				this.car.updatePosition();
			} catch (CarCannotReverseException e) {
				fail("Car tried to reverse");
				e.printStackTrace();
			}
		}
		
		this.car.changeLane(newlane);
		assertEquals(newlane, this.car.getLane());
		assertFalse(this.lane == newlane);
		assertEquals(0, this.car.getDrivenLaneDistance(), 0.1);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testChangeLaneException() {
		this.car.changeLane(null);
	}

	@Test
	public void testAccelerate() {
		GlobalConstants.getInstance().setTimestep(0.1F); // set the time step so we are sure to calculate rightly
		// TODO what is the delta here really?
		assertEquals(0, this.car.getSpeed(), 0.1); // initially this should be zero
		this.car.accelerate(1);
		try {
			this.car.updatePosition();
		} catch (CarCannotReverseException e) {
			fail("The car tried to reverse");
			e.printStackTrace();
		}	
		
		assertEquals(0.1, this.car.getSpeed(), 0.1);
		
		// drive the car around a bit
		this.car.accelerate(1);
		for (int i = 0; i < 10; i++) {
				try {
					car.updatePosition();
				} catch (CarCannotReverseException e) {
					fail("The car tried to reverse");
					e.printStackTrace();
				}
		}
		assertEquals(1.1, this.car.getSpeed(), 0.1);
		
		// break
		this.car.accelerate(-1);
		
		try {
			this.car.updatePosition();
		} catch (CarCannotReverseException e1) {
			fail("The car tried to reverse");
			e1.printStackTrace();
		}
		
		this.car.accelerate(-2);
		for (int i = 0; i < 5; i++) {
			try {
				car.updatePosition();
			} catch (CarCannotReverseException e) {
				fail("The car tried to reverse");
				e.printStackTrace();
			}
		}
		
		assertEquals(0, this.car.getSpeed(), 0.1);

	}

	@Test (expected=CarCannotReverseException.class)
	public void reverse() throws CarCannotReverseException {
		this.car.accelerate(-1);
		this.car.updatePosition();
	}
}
