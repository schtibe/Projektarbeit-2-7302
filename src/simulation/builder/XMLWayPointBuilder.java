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
	 */
	public XMLWayPointBuilder(Element e, IXMLWorldBuilder wBuilder) {
		super(e, wBuilder);

		String direction = this.elem.getAttributeValue("direction");
		if (direction.equals("right")) {
			this.direction = XMLWayPointBuilderFactory.direction.right;
		} else if (direction.equals("left")) {
			this.direction = XMLWayPointBuilderFactory.direction.left;
		} else {
			this.direction = XMLWayPointBuilderFactory.direction.both;
		}

		this.position = Float.parseFloat(this.elem
				.getAttributeValue("position"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLWayPointBuilderFactory.direction getDirection() {
		return this.direction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getPosition() {
		return this.position;
	}
}
