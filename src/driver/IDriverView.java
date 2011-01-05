package driver;

import common.IRectangle;
import common.IVector;

public interface IDriverView {

	/**
	 * sets the angle
	 * @param value The view Angle
	 */
	public abstract void setAngle(float value);

	/**
	 * Sets the distance
	 * @param value
	 */
	public abstract void setDistance(float value);

	/**
	 * Sets the direction vector
	 * @param value The direction of the view
	 */
	public abstract void setDirection(IVector value);

	/**
	 * Sets the position
	 * @param value Position of the vector
	 */
	public abstract void setPosition(IVector value);

	/**
	 * Get the angle
	 * @return
	 */
	public abstract float getAngle();

	/**
	 * get the distance
	 * @return
	 */
	public abstract float getDistance();

	/**
	 * Get the position
	 * @return
	 */
	public abstract IVector getPosition();

	/**
	 * Get the direction vector
	 * @return
	 */
	public abstract IVector getDirection();
	
	/**
	 * Returns a clone of the actual DriverView
	 * @return
	 */
	public abstract IDriverView clone();

	public String toString();
	
	/**
	 * Check whether the way point lies in this area
	 * @param position
	 * @return
	 */
	public boolean checkWayPoint(IVector position);
	
	/**
	 * Return a Rectangle that represents the bounding box of the DriverView
	 */
	public IRectangle getBoundingBox();
}
