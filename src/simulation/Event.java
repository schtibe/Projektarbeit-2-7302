package simulation;

/**
 * The abstract event
 * 
 * @param <E>
 *            The real type of the event
 */
public abstract class Event<E> implements IEvent {
	/**
	 * The timestamp of the event
	 */
	protected long timestamp;

	/**
	 * The target to execute the event on
	 */
	protected IEventTarget<E> target;

	/**
	 * Initialize
	 * 
	 * @param timestamp
	 * @param target
	 */
	public Event(long timestamp, IEventTarget<E> target) {
		this.timestamp = timestamp;
		this.target = target;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getTimeStamp() {
		return this.timestamp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IEventTarget<E> getTarget() {
		return this.target;
	}

	/**
	 * Comparator
	 * 
	 * @return comparison result
	 */
	@Override
	public int compareTo(IEvent arg0) {
		return (int) (this.timestamp - arg0.getTimeStamp());
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract void handleEvent() throws Exception;
}
