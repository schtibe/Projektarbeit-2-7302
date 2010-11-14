package test.environment;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import common.IVector;
import common.Vector;

import driver.DriverView;
import environment.IPlacable;
import environment.SpeedWayPoint;
import environment.WayPoint;
import environment.WayPointManager;

public class WayPointManagerTest extends TestCase {

	IVector direction;
	IVector position;
	IVector upperBound;
	IVector lowerBound;
	IVector lowerBoundInPlace;
	IVector upperBoundInPlace;
	float length = 100;
	float angle = (float) (Math.PI / 2);
	DriverView testView;

	@Before
	public void setUp() throws Exception {

		this.direction = new Vector(new float[] { 20, 20 });
		this.position = new Vector(new float[] { 10, 10 });

		testView = new DriverView(angle, length);
		testView.setPosition(this.position);
		testView.setDirection(this.direction);
		float halfViewAngle = testView.getAngle() / 2;
		upperBound = ((testView.getDirection().rotate(halfViewAngle))
				.normalize()).multiply(testView.getDistance());
		lowerBound = ((testView.getDirection().rotate(-halfViewAngle))
				.normalize()).multiply(testView.getDistance());

	}

	@Test
	public void testFOVAngle() {
		assertEquals((float) (Math.PI / 2), testView.getAngle(), 0.0001);
	}

	@Test
	public void testDirectionAngle() {
		assertEquals((float) (Math.PI) / 4, testView.getDirection().getAngle(),
				0.0001);
	}

	@Test
	public void testUpperBound() {
		float halfViewAngle = testView.getAngle() / 2;
		upperBound = ((testView.getDirection().rotate(halfViewAngle))
				.normalize()).multiply(testView.getDistance());
		;
		assertEquals(0f, upperBound.getComponent(0), 0.1);
		assertEquals(100f, upperBound.getComponent(1), 0.1);
	}

	@Test
	public void testLowerBound() {
		float halfViewAngle = testView.getAngle() / 2;
		this.lowerBound = ((testView.getDirection().rotate(-halfViewAngle))
				.normalize()).multiply(testView.getDistance());
		assertEquals(100f, lowerBound.getComponent(0), 0.1);
		assertEquals(0f, lowerBound.getComponent(1), 0.1);
	}

	@Test
	public void testMiniMax() {
		lowerBoundInPlace = testView.getPosition().add(lowerBound);
		upperBoundInPlace = testView.getPosition().add(upperBound);
		System.out.println(lowerBoundInPlace + ":" + upperBoundInPlace);
		IVector[] vectors = new IVector[] { lowerBoundInPlace,
				upperBoundInPlace };
		IVector min = testView.getPosition().clone();
		IVector max = testView.getPosition().clone();
		for (IVector vec : vectors) {
			float minX = min.getComponent(0);
			float minY = min.getComponent(1);
			float maxX = max.getComponent(0);
			float maxY = max.getComponent(1);
			if (vec.getComponent(0) < minX) {
				minX = vec.getComponent(0);
			}
			if (vec.getComponent(1) < minY) {
				minY = vec.getComponent(1);
			}
			min = new Vector(new float[] { minX, minY });
			if (vec.getComponent(0) > maxX) {
				maxX = vec.getComponent(0);
			}
			if (vec.getComponent(1) > maxY) {
				maxY = vec.getComponent(1);
			}
			max = new Vector(new float[] { maxX, maxY });
		}
		assertEquals(110, max.getComponent(0), 0.1);
		assertEquals(10, min.getComponent(0), 0.1);
		assertEquals(110, max.getComponent(1), 0.1);
		assertEquals(10, min.getComponent(1), 0.1);
	}
	@Test
	public void testAddWayPoint() {
		lowerBoundInPlace = testView.getPosition().add(lowerBound);
		upperBoundInPlace = testView.getPosition().add(upperBound);
		IVector[] vectors = new IVector[] { lowerBoundInPlace,
				upperBoundInPlace };
		IVector min = testView.getPosition().clone();
		IVector max = testView.getPosition().clone();
		for (IVector vec : vectors) {
			float minX = min.getComponent(0);
			float minY = min.getComponent(1);
			float maxX = max.getComponent(0);
			float maxY = max.getComponent(1);
			if (vec.getComponent(0) < minX) {
				minX = vec.getComponent(0);
			}
			if (vec.getComponent(1) < minY) {
				minY = vec.getComponent(1);
			}
			min = new Vector(new float[] { minX, minY });
			if (vec.getComponent(0) > maxX) {
				maxX = vec.getComponent(0);
			}
			if (vec.getComponent(1) > maxY) {
				maxY = vec.getComponent(1);
			}
			max = new Vector(new float[] { maxX, maxY });
		}
		try{
			WayPoint waypoint = new SpeedWayPoint(null,40,40);
			WayPoint otra = new SpeedWayPoint(null,180,18);
			WayPointManager.getInstance().add(waypoint);
			WayPointManager.getInstance().add(otra);
			WayPointManager.getInstance().setWayPoints();
			List<IPlacable> result = WayPointManager.getInstance().findWayPoints(testView);
			for (IPlacable point :result){
				if (point == waypoint){
					System.out.println("yes");
				}else{
					System.out.println("wrong");
				}
			}
		}catch (Exception e){
			System.out.println(e);
		}
	}
}
