package environment;

import java.util.List;

import common.IVector;

public interface IRoad extends ITrafficCarrier{
	/**
	 * Set the left lanes
	 * 
	 * @param leftLanes the leftLanes to set
	 */
	public void setLeftLanes(List<ILane> leftLanes);
	
	/**
	 * Set the right lanes
	 * @param rightLanes
	 */
	public void setRightLanes(List<ILane> rightLanes);
	
	
	
	/**
	 * Set the start point of the road
	 * 
	 * @param startPoint the startPoint to set
	 */
	public void setStartPoint(IVector startPoint);
	
	/**
	 * Set the start point
	 * 
	 * @param endPoint the endPoint to set (relative to the startPoint)
	 */
	public void setEndPoint(IVector endPoint);
	
	/**
	 * Return the end point
	 * 
	 * @return the endPoint (relative to the startPoint)
	 */
	public IVector getEndPoint();
	
	/**
	 * Return the start point
	 * 
	 * @return the startPoint
	 */
	public IVector getStartPoint();
}
