package common;

/**
 * A rectangle 
 */
public interface IRectangle {
	/**
	 * Get the position of the bottom left corner
	 * @return Bottom left corner
	 */
	public IVector getBottomLeft ();
	
	/**
	 * Get the position of the top right corner
	 * @return Top right corner
	 */
	public IVector getTopRight ();
	
	/**
	 * Get the the width of the rectangle
	 * @return Width of rectangle
	 */
	public float getWidth();
	
	/**
	 * Get the height of the rectangle
	 * @return Height of rectangle
	 */
	public float getHeight();
}
