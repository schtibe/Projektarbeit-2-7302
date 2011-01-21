package driver;

/**
 * Interface for the activators for acceleration and deceleration
 */
public interface IActivator {
	/**
	 * Return the given value
	 * @return value
	 */
	public float getValue();
	
	/**
	 * Set the given value
	 * @param amnt Value
	 */
	public void setValue(float amnt);
}
