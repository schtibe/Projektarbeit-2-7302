package environment;

import java.util.List;

import common.IVector;

public interface ILaneSegment<E> {
	/**
	 * Return the start point 
	 * @return
	 */
	public IVector getStartPoint();
	
	/**
	 * Return the end point
	 * @return
	 */
	public IVector getEndPoint();
	
	/**
	 * Return the distance on the lane where the lane segment begins
	 * 
	 * @see Lane#getLenghtBeforeSegment
	 * @return The distance on the lane where the lane segment begins
	 */
	public float getDistanceOnLane();
	
	/**
	 * Set the distance on the lane where the lane segment begins
	 * 
	 * @see Lane#getLenghtBeforeSegment
	 * @param distanceOnLane The distance on the lane where the lane segment begins
	 */
	public void setDistanceOnLane(float distanceOnLane);
	
	/**
	 * Set the next lane segment this one is connected to
	 * 
	 * @param LaneSegment
	 */
	public void setNextLaneSegment(E LaneSegment);
	
	/**
	 * Return the length of the lane segment 
	 * 
	 * @return
	 */
	public float getLength();
	
	/**
	 * Return the next lane segment
	 * 
	 * @return
	 */
	public E getNextLaneSegment();
	
	/**
	 * Return the vehicle position 
	 * 
	 * @param segmentLength
	 * @return
	 */
	public IVector getVehiclePosition(float segmentLength);
	
	/**
	 * returns teh array
	 * @return
	 */
	
	/**
	 * {@deprecated}
	 */
	
	public IVector[] getBezierPoints();
	
	/**
	 * getter for id
	 * @return
	 */
	
	public int getId();
	
	/**
	 * setter for id
	 * @param id
	 */
	
	public void setId(int id);
	
	/**
	 * to string
	 * @return String
	 */
	
	public String toString();
	
	/**
	 * {@deprecated}
	 * @return
	 */
	
	public List<SignWayPoint> getAllWayPoints();
	
	/**
	 * returns a point on the curve
	 * @param middle
	 * @return IVector
	 */
	
	public IVector getPointOnCurve(float middle);
}
