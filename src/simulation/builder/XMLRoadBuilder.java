package simulation.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdom.Element;

import common.IVector;
import common.Vector;

import environment.ILane;
import environment.IRoad;
import environment.Road;

/**
 * Builder for the roads
 * 
 * @todo Create interface
 */
public class XMLRoadBuilder extends XMLObjectBuilder {
	/**
	 * The road to build
	 */
	private IRoad road;

	/**
	 * The position of the road
	 */
	private IVector position;

	/**
	 * The road segments of the road
	 */
	private List<IXMLRoadSegmentBuilder> roadSegments;

	/**
	 * Initialize
	 */
	public XMLRoadBuilder(Element e, IXMLWorldBuilder wBuilder)
			throws InvalidXMLException {
		super(e, wBuilder);
		this.position = this.getRoadPosition();
		this.road = this.createRoad();
	}

	/**
	 * Return the road position
	 * 
	 * @return road position
	 */
	protected IVector getRoadPosition() {
		return new Vector(new float[] {
				Float.parseFloat(this.elem.getAttributeValue("startX")),
				Float.parseFloat(this.elem.getAttributeValue("startY")) });
	}

	/**
	 * Create a road
	 * 
	 * @return a road
	 * @throws InvalidXMLException
	 */
	protected IRoad createRoad() throws InvalidXMLException {
		List<XMLRightLaneBuilder> rightLaneBuilders = this.getRightLanes();
		List<XMLLeftLaneBuilder> leftLaneBuilders = this.getLeftLanes();

		List<ILane> leftLanes = new ArrayList<ILane>();
		List<ILane> rightLanes = new ArrayList<ILane>();

		// Build and add the right lanes
		int counter = 0;
		XMLLaneBuilder lastLane = null;
		for (XMLLaneBuilder lane : rightLaneBuilders) {
			if (lastLane != null) {
				counter += lastLane.getLaneWidth();
			}
			rightLanes.add(lane.createLane(this.getRoadSegments(), counter));
			lastLane = lane;
		}

		// Build and add the left lanes
		// reverse the road segments
		counter = 0;
		for (XMLLaneBuilder lane : leftLaneBuilders) {
			// this lane has to have the end point of the last road segment
			leftLanes.add(lane.createLane(this.getRoadSegmentsReversed(),
					counter));
			counter++;
		}

		return new Road(rightLanes, leftLanes, this.position, this
				.getRoadSegments().get(this.getRoadSegments().size() - 1)
				.getEndPoint().clone());
	}

	/**
	 * Return the lane segments (ordered)
	 * 
	 * @return
	 */
	protected List<IXMLRoadSegmentBuilder> getRoadSegments() {
		if (this.roadSegments == null) {
			this.roadSegments = this.readRoadSegments();
		}

		return this.roadSegments;
	}

	/**
	 * Return the lane segments ordered but reversed
	 */
	protected List<IXMLRoadSegmentBuilder> getRoadSegmentsReversed() {
		List<IXMLRoadSegmentBuilder> roadSegments = this.readRoadSegments();
		List<IXMLRoadSegmentBuilder> reversedRoadSegments = new ArrayList<IXMLRoadSegmentBuilder>();

		for (int i = roadSegments.size() - 1; i >= 0; i--) {
			IVector startPoint = roadSegments.get(i).getStartPoint();
			roadSegments.get(i)
					.setStartPoint(roadSegments.get(i).getEndPoint());
			roadSegments.get(i).setEndPoint(startPoint);
			reversedRoadSegments.add(roadSegments.get(i));
		}

		return reversedRoadSegments;
	}

	/**
	 * Read the road segments and return them in a list
	 * 
	 * The lane segments cannot be ordered at the moment. They have to be
	 * ordered in the XML file
	 * 
	 * @return
	 */
	protected List<IXMLRoadSegmentBuilder> readRoadSegments() {
		List<?> roadSegmentsElem = this.elem.getChild("roadsegments")
				.getChildren("roadsegment");
		List<IXMLRoadSegmentBuilder> roadSegments = new ArrayList<IXMLRoadSegmentBuilder>();
		for (int i = 0; i < roadSegmentsElem.size(); i++) {
			roadSegments.add(new XMLRoadSegmentBuilder(
					(Element) roadSegmentsElem.get(i), this.getWorldBuilder(),
					this.position));
		}

		Collections.sort(roadSegments);

		return roadSegments;
	}

	/**
	 * Return a list of right lanes
	 * 
	 * @returns
	 */
	protected List<XMLRightLaneBuilder> getRightLanes() {
		List<?> rightLanesElem = this.elem.getChild("rightlanes").getChildren(
				"lane");
		List<XMLRightLaneBuilder> rightLanes = new ArrayList<XMLRightLaneBuilder>();

		for (int i = 0; i < rightLanesElem.size(); i++) {
			rightLanes.add(
				new XMLRightLaneBuilder(
					(Element) rightLanesElem.get(i), 
					position,
					this.worldBuilder
				)
			);
		}

		return rightLanes;
	}

	/**
	 * Return a list of left lanes
	 * 
	 * @return
	 */
	protected List<XMLLeftLaneBuilder> getLeftLanes() {
		List<?> leftLanesElem = this.elem.getChild("leftlanes").getChildren(
				"lane");
		List<XMLLeftLaneBuilder> leftLanes = new ArrayList<XMLLeftLaneBuilder>();

		for (int i = 0; i < leftLanesElem.size(); i++) {
			leftLanes.add(
				new XMLLeftLaneBuilder(
					(Element) leftLanesElem.get(i), 
					position,
					this.worldBuilder
				)
			);
		}

		return leftLanes;
	}

	/**
	 * @return the road
	 */
	public IRoad getRoad() {
		return road;
	}
}
