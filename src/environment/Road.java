package environment;

import java.util.List;

import common.IVector;



public class Road implements IRoad {
	/**
	 * The lanes on the left
	 */
	private List<ILane> leftLanes;
	
	/**
	 * The lanes on the right
	 */
	private List<ILane> rightLanes;
	
	/**
	 * The position of the road
	 */
	private IVector startPoint;
	
	/**
	 * The ending point of the road
	 */
	private IVector endPoint;
	
	/**
	 * the road constructor
	 * @param rightLanes
	 * @param leftLanes
	 * @param startPoint
	 * @param endPoint
	 */
	
	public Road(List<ILane> rightLanes, List<ILane> leftLanes, 
			IVector startPoint,IVector endPoint) {
		this.setLeftLanes(leftLanes);
		this.setRightLanes(rightLanes);
		this.setStartPoint(startPoint);
		this.setEndPoint (endPoint);
		//this.calculateLaneStartPoints();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLeftLanes(List<ILane> leftLanes) {
		this.leftLanes = leftLanes;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ILane> getLeftLanes() {
		return leftLanes;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRightLanes(List<ILane> rightLanes) {
		this.rightLanes = rightLanes;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ILane> getRightLanes() {
		return rightLanes;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setStartPoint(IVector startPoint) {
		this.startPoint = startPoint;
	}


	/**
	 * {@inheritDoc}
	 */
	public IVector getStartPoint() {
		return startPoint;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setEndPoint(IVector endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * {@inheritDoc}
	 */
	public IVector getEndPoint() {
		return endPoint;
	}
}
