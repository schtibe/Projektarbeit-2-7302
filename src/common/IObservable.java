package common;

/**
 * Interface for objects that can be observed
 */
public interface IObservable {
	/**
	 * Register a new observer
	 * @param obs The observer to register
	 */
	public void register (IObserver obs);
	
	/**
	 * Remove an observer
	 * @param obs The observer to remove
	 */
	public void remove (IObserver obs);
	
	/**
	 * Notify all observers 
	 * @param msg The notification message
	 */
	public void notify (String msg);
}
