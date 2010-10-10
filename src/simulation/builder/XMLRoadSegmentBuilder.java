package simulation.builder;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import simulation.builder.XMLLaneBuilder.directionType;

import common.IVector;
import common.Vector;

import environment.LaneSegmentLinear;

public class XMLRoadSegmentBuilder extends XMLObjectBuilder implements
		IXMLRoadSegmentBuilder {
	/**
	 * The id of the road segment in the XML
	 */
	protected int id;

	/**
	 * The start and end points
	 */
	protected IVector startPoint, endPoint;

	/**
	 * The order of the road segments
	 */
	protected int order;

	/**
	 * The way points on the segment
	 */
	protected List<IXMLWayPointBuilder> wayPoints;

	/**
	 * Initialize all variables
	 * 
	 * @param e
	 *            The Element in the XML
	 * @param wBuilder
	 *            The world builder reference
	 * @param roadPosition
	 *            The position of the road
	 */
	public XMLRoadSegmentBuilder(Element e, IXMLWorldBuilder wBuilder,
			IVector roadPosition) {
		super(e, wBuilder);

		this.setStartPoint(new Vector(new float[] {
				Float.parseFloat(e.getAttributeValue("startX")),
				Float.parseFloat(e.getAttributeValue("startY")) })
				.add(roadPosition));
		this.setEndPoint(new Vector(new float[] {
				Float.parseFloat(e.getAttributeValue("endX")),
				Float.parseFloat(e.getAttributeValue("endY")) })
				.add(roadPosition));

		this.order = Integer.parseInt(this.elem.getAttributeValue("order"));

		this.id = this.order;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(IXMLRoadSegmentBuilder arg0) {
		if (arg0.getOrder() < this.order) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * what's this for?
	 */
	public void setEndPoint(IVector endPoint) {
		this.endPoint = endPoint.clone();
	}

	/**
	 * {@inheritDoc}
	 */
	public IVector getEndPoint() {
		return endPoint;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * what's this for?
	 */
	public void setStartPoint(IVector startPoint) {
		this.startPoint = startPoint.clone();
	}

	/**
	 * {@inheritDoc}
	 */
	public IVector getStartPoint() {
		return startPoint;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	public List<IXMLWayPointBuilder> readWayPoints() {

		List<IXMLWayPointBuilder> wayPoints = new ArrayList<IXMLWayPointBuilder>();
		List<?> wayPointElems = new ArrayList<Element>();

		try {
			wayPointElems = this.elem.getChild("waypoints").getChildren();
		} catch (NullPointerException e) {
			// if there are no waypoints - doesn't matter
		}

		for (int i = 0; i < wayPointElems.size(); i++) {
			wayPoints.add(XMLWayPointBuilderFactory.getWayPointBuilder(
					(Element) wayPointElems.get(i), this.getWorldBuilder()));
		}
		return wayPoints;
	}

	/**
	 * {@inheritDoc}
	 */
	public LaneSegmentLinear createLaneSegment(float offset,
			directionType direction) throws Exception {
		IVector vector = endPoint.sub(startPoint);
		IVector perp = this.getPerpVector(vector).normalize();

		perp = perp.multiply(offset);
		startPoint = startPoint.add(perp);
		endPoint = endPoint.add(perp);

		this.getWorldBuilder().setCoordinates(startPoint);
		this.getWorldBuilder().setCoordinates(endPoint);
		return new LaneSegmentLinear(this.id, startPoint, endPoint);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getOrder() {
		return this.order;
	}
}
