package environment;

import driver.Animus;

public interface IWayPoint extends Comparable<IWayPoint>,IPlacable {
	
	/**
	 * return the lane to which the way point belongs
	 * @return
	 */
	
	ILane getLane();
	
	/**
	 * visitor methos on the way point
	 * @param animus
	 */
	
	public void visitHandleWayPoint(Animus animus);
	
	/**
	 * return a string representation
	 * @return String
	 */
	
	public String toString();
}
