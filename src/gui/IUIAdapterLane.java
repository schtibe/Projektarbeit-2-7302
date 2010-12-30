package gui;

import java.util.List;

import org.newdawn.slick.geom.Path;

import common.IVector;

import environment.ILane;

/**
 * The adapter interface for a lane
 * @param <E> the class to adapt
 */
public interface IUIAdapterLane<E> extends IUIAdapter<E>, IUIAdapterDrawable {

	/**
	 * Get the Path
	 * @return the path
	 */
	public abstract Path getPath();
	
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

	/**
	 * Return whether a vehicle can be placed on this lane
	 * 
	 * Vehicles cannot be placed on some lanes, e.g. on those
	 * that are in junctions. Return here if it is possible to
	 * place a vehicle on this lane.
	 * @return
	 */
	public abstract boolean vehiclePlacable();
	
	/**
	 * Return the position on the lane
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public float getPositionOnLane(int mouseX, int mouseY);
	
	public List<Path> getLaneSegmentPaths();
}