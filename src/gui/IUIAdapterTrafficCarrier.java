package gui;

import java.util.List;

import common.IVector;

/**
 * The adapter interface for a traffic carrier
 * @param <E> the class to adapt	
 */
public interface IUIAdapterTrafficCarrier<E> extends IUIAdapter<E>{

	/**
	 * Get all IUIAdapterLane 
	 * @return a list of IUIAdapterLane
	 */
	public abstract List<IUIAdapterLane<?>> getLanes();
	
	/**
	 * Get the max pos of this object
	 * @return the max pos of this object
	 */
	public abstract IVector getMaxPos();
	
	/**
	 * Get the min pos of this object
	 * @return the min pos of this object
	 */
	public abstract IVector getMinPos();
	
	/**
	 * Set the scale of this object
	 * @scale the scale factor
	 */
	public abstract void setScale(float scale) throws Exception;
	
	/**
	 * Move the object to this pos
	 * @pos the new position of this object
	 */
	public abstract void move(IVector pos);
}