package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

/**
 * The adpater interface for the vehicle
 * @param <E> the class to adapt
 */
public interface IUIAdapterVehicle<E> extends IUIAdapter<E> {

	/**
	 * Get the shape which represents this object
	 * @return the shape which represents this object
	 */
	public Shape getBoundingBox();
	
	/**
	 * Generates a sting, containing the relevant data
	 * @return a string
	 */
	@Override
	public String toString();

	/**
	 * Draw the object
	 * @param g The draw utility
	 */
	public void draw(Graphics g);
}