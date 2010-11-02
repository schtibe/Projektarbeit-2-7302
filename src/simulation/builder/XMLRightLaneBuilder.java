package simulation.builder;

import org.jdom.Element;

import common.IVector;

/**
 * Builder for the right lane
 * 
 */
public class XMLRightLaneBuilder extends XMLLaneBuilder {

	/**
	 * Right lane builder
	 */
	public XMLRightLaneBuilder(Element e, IVector roadPosition, IXMLWorldBuilder wb) {
		super(e, roadPosition, wb);

		this.direction = directionType.right;
	}
}
