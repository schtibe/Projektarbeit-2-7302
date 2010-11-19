package environment;

import java.util.List;

import common.IVector;



public interface ILane {
	/**
	 * Calculate the vehicle's position
	 * 
	 * @param drivenLaneDistance
	 */
	public IVector getPositionOnLane(float drivenLaneDistance) throws LaneLengthExceededException;
	
	/**
	 * Calculate the position on a lane by the percentage
	 * @param position
	 * @return
	 * @throws LaneLengthExceededException
	 */
	public IVector getPositionOnLaneByPercentage(float position) throws LaneLengthExceededException; 
	
	/**
	 * Return all sign way points that a car can see
	 * 
	 * @param lanePosition The position of the car on the lane
	 * @param viewField    The view field of the driver
	 */
	//public List<IWayPoint> getWayPointsAtPosition(float lanePosition, float viewField);
	
	/**
	 * Returns the first lane segment
	 * @return Returns the first lane segment
	 */
	public ILaneSegment<?> getFirstILaneSegment();
	
	/**
	 * Returns the last lane segment
	 * @return Returns the last lane segment
	 */
	
	public ILaneSegment<?> getLastILaneSegment();
		
	/**
	 * sets the junction to which this lane is connected
	 */
	
	public void setJunction(IJunction junction);
	
	/**
	 * get the junction to which this lane is connected
	 * @return IJunction
	 */
	
	public IJunction getJunction();
	
	/**
	 * 
	 * @return a list of IVector
	 */
	
	public List<IVector[]> getLanePath();
	
	/**
	 * Return the start position
	 * @return the start position
	 */
	public IVector getStartPosition();
	
	/**
	 * get the lanes length
	 * @return the length 
	 */
	public float getLength();
	
	/**
	 * Add a way point to the lane
	 * 
	 * @param wp
	 */
	public void addWayPoint(SignWayPoint wp);
}
