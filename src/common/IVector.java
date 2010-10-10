package common;

public interface IVector extends Comparable<IVector> {

	/**
	 * adding two vectors
	 * 
	 * @param vector
	 * @return the resulting vector
	 */

	public IVector add(IVector vector);

	/**
	 * subtracting two vectors
	 * 
	 * @param vector
	 * @return the resulting vector
	 */

	public IVector sub(IVector vector);

	/**
	 * the norm of this vector
	 * 
	 * @return
	 */

	public float norm();

	/**
	 * the cross product of two vectors if defined
	 * 
	 * @param vector
	 * @return
	 */

	public IVector cross(IVector vector);

	/**
	 * the dot product of two vectors
	 * 
	 * @param vector
	 * @return
	 */

	public float dot(IVector vector);

	/**
	 * returns the component at index [0,1,..]
	 * 
	 * @param index
	 * @return
	 */

	public float getComponent(int index);

	/**
	 * normalize the vector
	 * 
	 * @return a vector with length 1.0 in the same direction
	 */

	public IVector normalize();

	/**
	 * returns a duplicate of this vector
	 * 
	 * @return
	 */

	public IVector clone();

	/**
	 * returns a multiple of the factor (stretched)
	 * 
	 * @param factor
	 * @return
	 */

	public IVector multiply(float factor);

	/**
	 * returns x: value, y:value
	 * 
	 * @return
	 */

	public String toString();

	/**
	 * returns the vector rotated by angle, angle is measured in radians
	 * 
	 * @param angle
	 * @return
	 */

	public IVector rotate(float angle);

	/**
	 * the result is the angle between this vector and the x axis in radians
	 * 
	 * @return
	 */

	public float getAngle();
	// public int compareTo(IVector vector);
}
