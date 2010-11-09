package simulation.builder;

import org.jdom.Element;

public abstract class XMLWayPointBuilder extends XMLObjectBuilder implements
		IXMLWayPointBuilder {

	/**
	 * The direction of the waypoint
	 */
	protected XMLWayPointBuilderFactory.direction direction;

	/**
	 * The position
	 */
	protected float position;

	/**
	 * Waypoint creator
	 * 
	 * @param e
	 * @param wBuilder
	 * @throws InvalidXMLException 
	 */
	public XMLWayPointBuilder(Element e) throws InvalidXMLException {
		super(e);

		this.position = Float.parseFloat(this.elem
				.getAttributeValue("position"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getPosition() {
		return this.position;
	}
}
