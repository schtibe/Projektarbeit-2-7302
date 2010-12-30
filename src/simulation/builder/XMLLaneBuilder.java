package simulation.builder;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import common.IVector;

import environment.ILaneSegmentLinear;
import environment.Lane;

/**
 * Abstract class for lane builders
 * 
 * @TODO there should probably be an interface
 */
public abstract class XMLLaneBuilder extends LaneBuilder implements
		IXMLObjectBuilder {
	/**
	 * The available directions for the factory
	 */
	public enum directionType {
		right, left
	}

	/**
	 * The direction this lane has
	 */
	protected directionType direction = directionType.right;

	/**
	 * The width of the lane
	 */
	protected float laneWidth = 1;

	/**
	 * The position of the overlying road
	 */
	protected IVector roadPosition;

	/**
	 * The XML element
	 */
	protected Element elem;

	/**
	 * Construct
	 * 
	 * @param e The XML element
	 * @param roadPosition
	 */
	public XMLLaneBuilder(Element e, IVector roadPosition) {
		this.elem = e;
		this.roadPosition = roadPosition.clone();

		this.laneWidth = Float.parseFloat(this.elem.getAttributeValue("width"));
	}

	/**
	 * @param laneWidth
	 *            the laneWidth to set
	 */
	public void setLaneWidth(float laneWidth) {
		this.laneWidth = laneWidth;
	}

	/**
	 * @return the laneWidth
	 */
	public float getLaneWidth() {
		return laneWidth;
	}

	/**
	 * Create and return the lane
	 * 
	 * @param roadSegments
	 * @return
	 * @throws IllegalArgumentException
	 * @throws InvalidXMLException
	 */
	public Lane createLane(List<IXMLRoadSegmentBuilder> roadSegments,
			int counter) throws IllegalArgumentException, InvalidXMLException {
		List<ILaneSegmentLinear> laneSegments = new ArrayList<ILaneSegmentLinear>();

		if (roadSegments.size() == 0) {
			throw new IllegalArgumentException("received no road segments");
		}

		// build all the linear right lane segments
		for (IXMLRoadSegmentBuilder segment : roadSegments) {
			try {
				laneSegments.add(
					segment.createLaneSegment(
						counter	+ this.laneWidth / 2, 
						this.direction
					)
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// this lane has to have the start point of the road
		ILaneSegmentLinear startSegment = this
				.getCompleteLaneSegmentList(laneSegments);
		
		Lane lane = new Lane(
			startSegment.getStartPoint().clone(), 
			startSegment,
			this.laneWidth,
			true
		);
		
		for (IXMLWayPointBuilder wp: this.readWayPoints()) {
			lane.addWayPoint(wp.createWayPoint(lane));
		}
		
		return lane;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 * @throws InvalidXMLException 
	 */
	public List<IXMLWayPointBuilder> readWayPoints() throws InvalidXMLException {
		List<IXMLWayPointBuilder> wayPoints = new ArrayList<IXMLWayPointBuilder>();
		List<?> wayPointElems = new ArrayList<Element>();

		try {
			wayPointElems = this.elem.getChild("waypoints").getChildren();
		} catch (NullPointerException e) {
			// if there are no waypoints - doesn't matter
		}

		for (int i = 0; i < wayPointElems.size(); i++) {
			wayPoints.add(
				XMLWayPointBuilderFactory.getWayPointBuilder(
					(Element) wayPointElems.get(i)
				)
			);
		}
		return wayPoints;
	}
}
