package common;

/**
 * Interface for objects that can be registered
 * to an observer
 */
public interface IObserver {
	/**
	 * Receive an notification from an observable
	 * @param message The notification message
	 */
	public void update (String message);
}
