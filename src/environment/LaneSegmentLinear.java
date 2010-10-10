package environment;

import common.IVector;


public class LaneSegmentLinear extends LaneSegment<ILaneSegmentQuadratic> 
		implements ILaneSegmentLinear {

	/**
	 * this is the constructor
	 * @param id
	 * @param startPoint
	 * @param endPoint
	 * @throws Exception
	 */
	
	public LaneSegmentLinear(int id, IVector startPoint, IVector endPoint) throws Exception{
		super(startPoint, endPoint);
		this.length = calculateLength();
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected float calculateLength() {
		return relativeEndPoint.norm();
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public IVector getVehiclePosition(float segmentLength) {
		float time = segmentLength/this.length;
		return getPointOnCurve(time);
		/*IVector result = relativeEndPoint.multiply(time);
		return result.add(startPoint);*/
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public IVector getPointOnCurve(float time){
		if (time >= 0f && time <= 1f){
			IVector result = relativeEndPoint.multiply(time);
			return result.add(this.startPoint);
		}else{
			return this.endPoint;
		}
	}
	
	@Override
	public IVector[] getBezierPoints() {
		IVector[] output = new IVector[4];
		output[0] = this.startPoint;
		output[1] = output[2] = output[3] = this.endPoint;
		return output;
	}
}
