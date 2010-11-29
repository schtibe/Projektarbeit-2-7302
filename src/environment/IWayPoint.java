package environment;

import gui.IUIAdapterWayPoint;

import common.IVector;

import driver.Animus;

public interface IWayPoint extends Comparable<IWayPoint>,IPlacable {
	
	/**
	 * return the lane to which the way point belongs
	 * @return
	 */
	ILane getLane();
	
	/**
	 * visitor methods on the way point
	 * @param animus
	 */
	public void visitHandleWayPoint(Animus animus);
	
	/**
	 * Vistor method to the UI Element factory
	 * @param factory
	 * @throws Exception 
	 */
	public IUIAdapterWayPoint<?> visitUIFactory(float scale, IVector offsetVector) throws Exception;
	
	/**
	 * return a string representation
	 * @return String
	 */
	@Override
	public String toString();
}