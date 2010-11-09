package simulation.builder;

import simulation.builder.XMLWayPointBuilderFactory.direction;
import environment.ILane;
import environment.SignWayPoint;

/**
 * The way point builder
 */
public interface IXMLWayPointBuilder {
	/**
	 * Return the position
	 * 
	 * @return position
	 */
	public float getPosition();

	/**
	 * Factory for way point
	 * 
	 * @param lane
	 * @return the creatrd way point
	 */
	public SignWayPoint createWayPoint(ILane lane);
}
