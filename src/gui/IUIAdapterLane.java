package gui;

import environment.ILane;

import org.newdawn.slick.geom.Shape;

import common.IVector;

/**
 * The adapter interface for a lane
 * @param <E> the class to adapt
 */
public interface IUIAdapterLane<E> extends IUIAdapter<E>{

	/**
	 * Get the Path
	 * @return the path
	 */
	public abstract Shape getPath();
	
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
	 * Set the scale
	 * @scale the new scale factor
	 */
	public abstract void setScale(float scale) throws Exception;
	
	/**
	 * Move the object to this pos
	 * @pos the new position of this object
	 */
	public abstract void move(IVector pos);
	
	/**
	 * get the object inside of this adapter
	 * @return the object inside of this adapter
	 */
	public ILane getOriginalLane();
}