package simulation;

import java.util.PriorityQueue;

import common.GlobalConstants;

/**
 * The queue of all events
 * 
 * This is implemented as a singleton
 */
public class EventQueue {
	/**
	 * The real instance
	 */
	private static EventQueue instance;

	/**
	 * The events to process
	 */
	private PriorityQueue<IEvent> events;

	private EventQueue() {
		events = new PriorityQueue<IEvent>();
	}

	/**
	 * Singleton
	 * 
	 * @return instance of the event queue
	 */
	public synchronized static EventQueue getInstance() {
		if (instance == null) {
			instance = new EventQueue();
		}
		return instance;
	}

	/**
	 * Add a new event to the queue
	 * 
	 * @param event
	 */
	public void addEvent(IEvent event) {
		events.add(event);
	}

	/**
	 * Return the next Event to Process according to the given timestamp
	 * 
	 * Returns null if there is no event to be processed with the given time
	 * stamp anymore.
	 * 
	 * @param timestamp
	 * @return next event or null if there is none
	 */
	public IEvent getNextEvent(long timestamp) {
		IEvent output = events.poll();
		if (output != null) {
			if (output.getTimeStamp() <= timestamp) {
				return output;
			} else {
				events.add(output);
				return null;
			}
		}
		return output;
	}
}
