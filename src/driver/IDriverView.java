package driver;

import common.IRectangle;
import common.IVector;

/**
 * The interface for driver views
 */
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
	 * @return The angle
	 */
	public abstract float getAngle();

	/**
	 * get the distance
	 * @return Distance
	 */
	public abstract float getDistance();

	/**
	 * Get the position
	 * @return Position
	 */
	public abstract IVector getPosition();

	/**
	 * Get the direction vector
	 * @return Direction
	 */
	public abstract IVector getDirection();
	
	/**
	 * Returns a clone of the actual DriverView
	 * @return The clone
	 */
	public abstract IDriverView clone();

	@Override
	public String toString();
	
	/**
	 * Check whether the way point lies in this area
	 * @param position
	 * @return Wheter it lies in the arey
	 */
	public boolean checkWayPoint(IVector position);
	
	/**
	 * Return a Rectangle that represents the bounding box of the DriverView
	 */
	public IRectangle getBoundingBox();
}
