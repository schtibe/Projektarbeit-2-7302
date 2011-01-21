package car;

/**
 * The dimensions of a vehicle
 */
public class VehicleDimension {
	/**
	 * The length of the vehicle
	 */
	private float length;

	/**
	 * The width of the vehicle
	 */
	private float width;

	/**
	 * The radius of the bounding circle
	 */
	private float radius;
	
	/**
	 * Initialise the vehicle
	 * @param width
	 * @param length
	 */
	public VehicleDimension(float width, float length) {
		this.width = width;
		this.length = length;
		this.updateBoundingRadius();
	}

	/**
	 * Return the length
	 * @return the length
	 */
	public float getLength() {
		return length;
	}

	/**
	 * Set the length
	 * @param length
	 */
	public void setLength(float length) {
		this.length = length;
		this.updateBoundingRadius();
	}

	/**
	 * Return the width
	 * @return
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Set the width
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
		this.updateBoundingRadius();
	}
	
	/**
	 * Get the bounding circle radius
	 * @return the bounding circles radius value
	 */
	public float getBoundingRadius (){
		return radius;
	}
	
	/**
	 * update the radius after change to width or height
	 */
	private void updateBoundingRadius(){
		radius = (float)Math.sqrt((length*length+width*width));
	}
}
