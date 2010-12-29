package environment;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import common.IVector;

public abstract class MovingWayPoint implements IWayPoint,IMovable {

	protected IVector position;

	public MovingWayPoint(IVector position) {
		this.position = position;
	}

	@Override
	public int compareTo(IWayPoint arg0) {
		throw new NotImplementedException();
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
