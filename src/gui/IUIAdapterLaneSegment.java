package gui;

import java.util.List;

import common.IVector;
import environment.ILaneSegment;

/**
 * The adapter interface for a lane segment
 * @param <E> the class to adapt
 */
public interface IUIAdapterLaneSegment<E> extends IUIAdapter<E> {
	/**
	 * Get an ordered list of all vectors
	 * @return an ordered list of all vectors
	 */
	public List<IVector> getPath();
	
	/**
	 * Return the start position of the lane segment
	 * @return
	 */
	public IVector getStartPosition();
}
