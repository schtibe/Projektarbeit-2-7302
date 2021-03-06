package environment;

import java.util.ArrayList;
import java.util.List;

import common.IVector;
import common.Vector;


public abstract class LaneSegment<E> implements ILaneSegment<E> {
	/**
	 * The id of the lane segment
	 */
	protected int id;

	/**
	 * The next lane segment
	 */
	protected E nextLaneSegment;
	
	/**
	 * The previous lane segment
	 */
	protected E previousLaneSegment; // maybe
	
	/**
	 * The vectors of the start and end points of the lane segment relative to the lane
	 */
	protected IVector startPoint, endPoint, relativeEndPoint;

	/**
	 * The distance on the lane where the lane segment begins
	 * 
	 * @see Lane#getLengthBeforeSegment
	 */
	protected float distanceOnLane;

	@Override
	public IVector getStartPoint() {
		return startPoint;
	}

	@Override
	public IVector getEndPoint() {
		return endPoint;
	}
	
	@Override
	public float getDistanceOnLane() {
		return distanceOnLane;
	}

	@Override
	public void setDistanceOnLane(float distanceOnLane) {
		this.distanceOnLane = distanceOnLane;
	}

	/**
	 * A list of all way points on the lane segment
	 */
	protected List<SignWayPoint> wayPoints = new ArrayList<SignWayPoint>();
	
	/**
	 * The length of the lane segment
	 */
	protected float length;
	
	/**
	 * Create the lane segment
	 * 
	 * @param startPoint 
	 * @param endPoint
	 * @param signWayPoints
	 */
	public LaneSegment(IVector startPoint, IVector endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.relativeEndPoint = this.getRelativePoint(endPoint);
		//this.length = this.calculateLength();
	}
	
	/**
	 * Calculate the length of a lane segment
	 *
	 * @return
	 */
	protected abstract float calculateLength();
	
	/**
	 * Return the relative points 
	 * 
	 * We don't calculate with the absolute coordinates of the
	 * lane segment, instead, relative coordinates to the 
	 * start point of the lane segments are used. 
 	 * @param point
	 * @return
	 */
	protected IVector getRelativePoint (IVector point){
		float xPos = point.getComponent(0)-startPoint.getComponent(0);
		float yPos = point.getComponent(1)-startPoint.getComponent(1);
		return new Vector(new float[]{xPos,yPos});
	}

	@Override
	public void setNextLaneSegment(E laneSegment) {
		this.nextLaneSegment = laneSegment;
	}

	@Override
	public float getLength() {
		return this.length;
	}
	
	@Override
	public E getNextLaneSegment() {
		return this.nextLaneSegment;
	}

	@Override
	public abstract IVector getVehiclePosition(float segmentLength);
	
	@Override
	public abstract IVector[] getBezierPoints();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		String ret = "LaneSegment id: " + this.id + "\n";
		ret += "Startpoint: " + this.startPoint + "\n";
		ret += "Endpoint: " + this.endPoint;
		return ret;
	}
	
	@Override
	public List<SignWayPoint> getAllWayPoints() {
		return this.wayPoints;
	}
}
