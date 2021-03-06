package simulation.builder;

import org.jdom.Element;

import common.IVector;

/**
 * Builder for the left lanes
 */
public class XMLLeftLaneBuilder extends XMLLaneBuilder {
	/**
	 * Set the direction to left
	 * 
	 * @param e
	 * @param roadPosition
	 */
	public XMLLeftLaneBuilder(Element e, IVector roadPosition) {
		super(e, roadPosition);

		this.direction = directionType.left;
	}
}
