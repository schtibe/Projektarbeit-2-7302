package gui;

import org.newdawn.slick.geom.Shape;

/**
 * The adpater interface for the way point
 * @param <E> the class to adapt
 */
public interface IUIAdapterWayPoint<E> extends IUIAdapter<E> {
	
	/**
	 * get the waypoint shape
	 * @return a shape which represents the waypoint
	 */
	public Shape getShape();
	
	/**
	 * To String
	 * @return a string
	 */
	public String toString();
}