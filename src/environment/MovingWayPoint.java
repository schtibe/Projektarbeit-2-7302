package environment;

import common.IVector;

public abstract class MovingWayPoint implements IWayPoint,IMovable {

	protected IVector position;

	public MovingWayPoint(IVector position) {
		this.position = position;
	}

	/**
	 * {@inheritDoc}
	 */
	public float getXPos() {
		return this.position.getComponent(0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public float getYPos() {
		return this.position.getComponent(1);
	}

	/**
	 * Update the position
	 * @param pos
	 */
	public void updatePosition(IVector pos) {
		this.position = pos;
	}
	@Override
	public String toString(){
		return "type: moving\ncoordinates: "+this.position.toString();
	}
}
