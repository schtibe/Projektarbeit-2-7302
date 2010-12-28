package environment;

import common.IVector;

/**
 * The Interface for movable placables
 */
public interface IMovable extends IPlacable {
	/**
	 * Update the position of the placable element
	 * 
	 * @param position Position of the placable
	 */
	public void updatePosition(IVector position);
}
