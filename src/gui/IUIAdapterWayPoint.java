package gui;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Shape;

/**
 * The adpater interface for the way point
 * @param <E> the class to adapt
 */
public interface IUIAdapterWayPoint<E> extends IUIAdapterColored<E> {
	
	/**
	 * get the waypoint shape
	 * @return a shape which represents the waypoint
	 */
	public Shape getShape();
	
	/**
	 * To String
	 * @return a string
	 */
	@Override
	public String toString();

	/**
	 * Draw the string next to the way point
	 * @param ttf The true type font to draw with
	 */
	public void drawString(TrueTypeFont ttf);
}
