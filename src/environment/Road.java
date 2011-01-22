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

	@Override
	public void setLeftLanes(List<ILane> leftLanes) {
		this.leftLanes = leftLanes;
	}

	@Override
	public List<ILane> getLeftLanes() {
		return leftLanes;
	}

	@Override
	public void setRightLanes(List<ILane> rightLanes) {
		this.rightLanes = rightLanes;
	}

	@Override
	public List<ILane> getRightLanes() {
		return rightLanes;
	}

	@Override
	public void setStartPoint(IVector startPoint) {
		this.startPoint = startPoint;
	}

	@Override
	public IVector getStartPoint() {
		return startPoint;
	}
	
	@Override
	public void setEndPoint(IVector endPoint) {
		this.endPoint = endPoint;
	}

	@Override
	public IVector getEndPoint() {
		return endPoint;
	}
}
