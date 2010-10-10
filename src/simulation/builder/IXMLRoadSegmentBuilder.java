package simulation.builder;

import simulation.builder.XMLLaneBuilder.directionType;

import common.IVector;

import environment.ILaneSegmentLinear;

/**
 * Factory class for the road segments
 */
public interface IXMLRoadSegmentBuilder extends
		Comparable<IXMLRoadSegmentBuilder> {
	/**
	 * @param endPoint
	 *            the endPoint to set
	 */
	public void setEndPoint(IVector endPoint);

	/**
	 * Get the end point
	 * 
	 * @return the endPoint
	 */
	public IVector getEndPoint();

	/**
	 * Set the start point
	 * 
	 * @param startPoint
	 *            the startPoint to set
	 */
	public void setStartPoint(IVector startPoint);

	/**
	 * The start point
	 * 
	 * @return the startPoint
	 */
	public IVector getStartPoint();

	/**
	 * Return a lane segment
	 * 
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	public ILaneSegmentLinear createLaneSegment(float offset,
			directionType direction) throws Exception;

	/**
	 * Return the order of the road segment
	 * 
	 * @return
	 */
	public abstract int getOrder();
}
