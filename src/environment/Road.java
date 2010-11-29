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
	@Override
	public void setLeftLanes(List<ILane> leftLanes) {
		this.leftLanes = leftLanes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ILane> getLeftLanes() {
		return leftLanes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRightLanes(List<ILane> rightLanes) {
		this.rightLanes = rightLanes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ILane> getRightLanes() {
		return rightLanes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStartPoint(IVector startPoint) {
		this.startPoint = startPoint;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector getStartPoint() {
		return startPoint;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEndPoint(IVector endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector getEndPoint() {
		return endPoint;
	}
}
