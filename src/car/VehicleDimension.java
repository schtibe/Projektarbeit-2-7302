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
	 * Initialize the vehicle
	 * @param width
	 * @param length
	 */
	public VehicleDimension(float width, float length) {
		this.width = width;
		this.length = length;
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
	}
}
