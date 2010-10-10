package simulation;

/**
 * Interface for event targets
 * 
 * Makes them implement a handle event method according to the given event type
 * 
 * @author user
 * 
 * @param <E>
 *            The type of Event that the target expects
 */
public interface IEventTarget<E> {
	/**
	 * Handle the event
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void handleEvent(E event) throws Exception;
}
