package simulation;

/**
 * Interface for events
 */
public interface IEvent extends Comparable<IEvent> {
	/**
	 * Return the event's time stamp
	 * 
	 * @return time stamp
	 */
	public long getTimeStamp();

	/**
	 * Return the event's target
	 * 
	 * @return event target
	 */
	public IEventTarget<?> getTarget();

	/**
	 * Handle the event
	 * 
	 * @throws Exception
	 */
	public void handleEvent() throws Exception;
}
