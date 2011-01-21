package environment;

import common.IVector;

public abstract class MovingWayPoint extends WayPoint implements IMovable {

	protected IVector position;

	public MovingWayPoint(IVector position) {
		this.position = position;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getXPos() {
		return this.position.getComponent(0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getYPos() {
		return this.position.getComponent(1);
	}

	/**
	 * Update the position
	 * @param pos
	 */
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
