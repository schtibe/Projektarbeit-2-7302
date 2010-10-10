package environment;

import java.util.List;

import common.IVector;



public interface ILane {
	/**
	 * Calculate the vehicle's position
	 * 
	 * @param drivenLaneDistance
	 * TODO the math
	 */
	public IVector getVehiclePosition(float drivenLaneDistance) throws LaneLengthExceededException;
	
	/**
	 * Return all sign way points that a car can see
	 * 
	 * @param lanePosition The position of the car on the lane
	 * @param viewField    The view field of the driver
	 */
	//public List<IWayPoint> getWayPointsAtPosition(float lanePosition, float viewField);
	
	/**
	 * Return the start position
	 * @return the start position
	 */
	//public IVector getStartPosition();
	
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
	 * Returns an ordered Array
	 * @return an Array full of polygon corners of this lane
	 */
	
	
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
}