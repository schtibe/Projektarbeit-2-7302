package simulation.builder;

import java.util.List;

import common.IVector;
import common.Vector;

import environment.ILaneSegmentLinear;
import environment.LaneSegmentQuadratic;

/**
 * Some helper methods to build lanes
 */
public class LaneBuilder {
	/**
	 * Create a linked list of lane segments
	 * 
	 * Always take two linear lane segment and create the quadratic (curved)
	 * lane segment in between. Link them together and return the first lane
	 * segment.
	 * 
	 * @param laneSegments
	 * @return
	 * @throws InvalidXMLException
	 */
	public ILaneSegmentLinear getCompleteLaneSegmentList(
			List<ILaneSegmentLinear> laneSegments) throws InvalidXMLException {

		if (laneSegments == null) {
			throw new IllegalArgumentException(
					"Got a null pointer instead of a list");
		}

		if (laneSegments.size() == 0) {
			throw new IllegalArgumentException(
					"There must be some lane segments");
		}

		ILaneSegmentLinear firstLaneSegment = laneSegments.get(0);
		ILaneSegmentLinear lastLaneSegment = null;

		for (ILaneSegmentLinear seg : laneSegments) {
			if (lastLaneSegment != null) {
				lastLaneSegment.setNextLaneSegment(this
						.getCurveBetweenSegments(lastLaneSegment, seg));
			}
			lastLaneSegment = seg;
		}

		return firstLaneSegment;
	}

	/**
	 * Create a curved lane segment
	 * 
	 * Calculate the bend point and create a quadratic lane segment. If two
	 * lanes are already crossing, an exception is thrown. If the angle is ....
	 * also exception...
	 * 
	 * @param laneSegment1
	 * @param laneSegment2
	 * @return The curved lane segment
	 * @throws InvalidXMLException
	 */
	public LaneSegmentQuadratic getCurveBetweenSegments(
			ILaneSegmentLinear laneSegment1, ILaneSegmentLinear laneSegment2)
			throws InvalidXMLException {

		// get the cross points
		IVector startPoint1  = laneSegment1.getStartPoint();
		IVector endPoint1    = laneSegment1.getEndPoint();
		IVector startPoint2  = laneSegment2.getStartPoint();
		IVector endPoint2    = laneSegment2.getEndPoint();

		IVector vector1      = endPoint1.sub(startPoint1);
		IVector vector2      = endPoint2.sub(startPoint2);

		// the angle of two vectors
		float aboveLine = vector1.getComponent(0) * vector2.getComponent(0)
				+ vector1.getComponent(1) * vector2.getComponent(1);
		float underLine = vector1.norm() * vector2.norm();
		float angle = (float) Math.acos(aboveLine / underLine); // which format
																// is this?

		if (angle < 0.1) {
			throw new InvalidXMLException("the lanes are parallel");
		}

		float t = (startPoint1.getComponent(1) * vector2.getComponent(0)
				- startPoint2.getComponent(1) * vector2.getComponent(0)
				- startPoint1.getComponent(0) * vector2.getComponent(1) + startPoint2
				.getComponent(0)
				* vector2.getComponent(1))
				/ (vector1.getComponent(0) * vector2.getComponent(1) - vector1
						.getComponent(1)
						* vector2.getComponent(0));

		if (t <= 1.0 && t >= 0.0) {
			throw new InvalidXMLException("lanes cross each other\n"+laneSegment1.toString()+":\n"+laneSegment2.toString());
		}

		// is u even necessary?
		/*
		 * float u = (startPoint1.getComponent(1)- startPoint2.getComponent(1) +
		 * t * vector1.getComponent(1) )/( vector2.getComponent(1) );
		 */
		float u = (startPoint1.getComponent(1) + t * vector1.getComponent(1) - startPoint2
				.getComponent(1))
				/ (vector2.getComponent(1));

		if (u <= 1.0 && u >= 0.0) {
			throw new InvalidXMLException("lanes cross each other\n"+laneSegment1.toString()+":\n"+laneSegment2.toString());
		}

		float bendPointX = startPoint1.getComponent(0) + t
				* vector1.getComponent(0);
		float bendPointY = startPoint1.getComponent(1) + t
				* vector1.getComponent(1);
		IVector bendPoint = new Vector(new float[] { bendPointX, bendPointY });

		LaneSegmentQuadratic lane = null;
		try {
			lane = new LaneSegmentQuadratic(endPoint1, startPoint2, bendPoint);
		} catch (Exception e) {
			e.printStackTrace();
		}

		lane.setNextLaneSegment(laneSegment2);

		return lane;
	}
}
