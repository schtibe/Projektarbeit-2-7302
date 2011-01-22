package environment;

import common.IVector;

public abstract class MovingWayPoint extends WayPoint implements IMovable {

	protected IVector position;
	
	/**
	 * Construct
	 * @param IVector position
	 */
	
	public MovingWayPoint(IVector position) {
		this.position = position;
	}

	@Override
	public float getXPos() {
		return this.position.getComponent(0);
	}
	
	@Override
	public float getYPos() {
		return this.position.getComponent(1);
	}

	@Override
	public void updatePosition(IVector pos) {
		this.position = pos;
	}
	
	@Override
	public String toString(){
		return "type: moving\ncoordinates: "+this.position.toString();
	}
	
	@Override
	public IVector getPosition() {
		return this.position;
	}
}
