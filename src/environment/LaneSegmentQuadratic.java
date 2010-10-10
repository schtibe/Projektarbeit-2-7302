package environment;

import common.IVector;



public class LaneSegmentQuadratic extends LaneSegment<ILaneSegmentLinear> 
		implements ILaneSegmentQuadratic {
	private IVector bendPoint, relativeBendPoint;
	
	/**
	 * the constructor
	 * @param startPoint
	 * @param endPoint
	 * @param bendPoint
	 * @throws Exception
	 */
	
	public LaneSegmentQuadratic(IVector startPoint, IVector endPoint, IVector bendPoint)throws Exception {
		super(startPoint, endPoint);
		if (endPoint.equals(bendPoint) || startPoint.equals(bendPoint)){
			throw new IllegalArgumentException();
		}
		this.bendPoint = bendPoint;
		this.relativeBendPoint = this.getRelativePoint(bendPoint);
		this.length = calculateLength();
	}
	
	/**
	 * Calculate the length of the curve
	 * 
	 * @return float
	 */
	protected float calculateLength() {
		double ax = startPoint.getComponent(0) - 2*bendPoint.getComponent(0) + endPoint.getComponent(0);
		double ay = startPoint.getComponent(1) - 2*bendPoint.getComponent(1) + endPoint.getComponent(1);
		double bx = 2*bendPoint.getComponent(0) - 2*startPoint.getComponent(0);
		double by = 2*bendPoint.getComponent(1) - 2*startPoint.getComponent(1);
		double A = 4*(ax*ax+ay*ay);
		double B = 4*(ax*bx+ay*by);
		double C = bx*bx+by*by;
		double p = 2*Math.sqrt(A+B+C);
		double q = Math.sqrt(A);
		double r = 2*A*q;
		double s = 2*Math.sqrt(C);
		double t = B/q;
		double length = (r*p + q*B*(p-s) + (4*C*A-B*B)*Math.log( (2*q+t+p)/(t+s) ) )/(4*r);
		return (float)length;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector getVehiclePosition(float segmentLength) {
		float time = segmentLength/length;
		return this.getPointOnCurve(time);
	}
	
	/**
	 * Return the bend point
	 * 
	 * @return
	 */
	public IVector getBendPoint() {
		return this.bendPoint;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public IVector[] getBezierPoints() {
		IVector[] output = new IVector[4];
		output[0] = this.startPoint;
		output[1] = output[3] = this.endPoint;
		output[2] = this.bendPoint;
		return output;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public IVector getPointOnCurve(float middle) {
		IVector result = relativeBendPoint.multiply(2*middle*(1-middle)).add(relativeEndPoint.multiply(middle*middle));
		
		return startPoint.add(result);
	}
	
}
