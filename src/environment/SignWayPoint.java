package environment;

import common.IVector;

public abstract class SignWayPoint extends StaticWayPoint {
	
	/**
	 * The position on the lane segment
	 */
	private float laneSegmentPosition;
	
	public SignWayPoint(ILane lane, IVector position) {
		super(lane, position);
	}

	/**
	 * Return the lane position
	 * @return
	 */
	public float getLanePosition() {
		return this.laneSegmentPosition;
	}
	
	/**
	 * compares two sign way points
	 * @param arg0
	 * @return
	 */
	public int compareTo(SignWayPoint arg0) {
		if (arg0.getLanePosition() == this.laneSegmentPosition) {
			return 0;
		} else if (arg0.getLanePosition() < this.laneSegmentPosition) {
			return 1;
		} else {
			return -1;
		}
	}
}
