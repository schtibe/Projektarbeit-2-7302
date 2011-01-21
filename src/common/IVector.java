package common;

/**
 * Interface for vectors
 */
public interface IVector extends Comparable<IVector> {
	/**
	 * adding two vectors
	 * 
	 * @param the vector to be added to this vector
	 * @return a vector as a result of the operation
	 */
	public IVector add(IVector vector);

	/**
	 * subtracting two vectors
	 * 
	 * @param the vector to be subtracted from this vector
	 * @return a vector as a result of the operation
	 */
	public IVector sub(IVector vector);

	/**
	 * the norm of this vector
	 * 
	 * @return The length of the vector
	 */
	public float norm();

	/**
	 * the cross product of two vectors if defined
	 * 
	 * @param the vector to calculate the cross product with this vector
	 * @return a vector as a result of the operation
	 */
	public IVector cross(IVector vector);

	/**
	 * the dot product of two vectors
	 * 
	 * @param the vector to calculate the dotproduct with this vector
	 * @return a float as a result of the operation
	 */
	public float dot(IVector vector);

	/**
	 * returns the component at index [0,1,..]
	 * 
	 * @param index of a component of the vector
	 * @return the components value
	 */
	public float getComponent(int index);

	/**
	 * normalise the vector
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
	 * @param factor to multiply this vector with
	 * @return a scaled copy of the actual vector
	 */
	public IVector multiply(float factor);

	/**
	 * returns x: value, y:value
	 * 
	 * @return
	 */
	@Override
	public String toString();

	/**
	 * returns the vector rotated by angle, angle is measured in radians
	 * 
	 * @param an angle in the radiant notation [0,2*PI]
	 * @return a vector rotated by the input angle
	 */
	public IVector rotate(float angle);

	/**
	 * the result is the angle between this vector and the x axis in radians
	 * 
	 * @return an angle in radiant
	 */
	public float getAngle();

}
