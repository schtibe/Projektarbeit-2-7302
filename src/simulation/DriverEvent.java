package simulation;

import environment.IJunctionDecision;

/**
 * Events for the driver
 */
public class DriverEvent extends Event<DriverEvent> {
	/**
	 * True if the driver should monitor the environment
	 */
	private boolean doMonitor = false;

	/**
	 * The decision if available
	 */
	private IJunctionDecision decision = null;

	/**
	 * Initialize
	 * 
	 * @param timestamp
	 * @param target
	 */
	public DriverEvent(long timestamp, IEventTarget<DriverEvent> target) {
		super(timestamp, target);
	}

	/**
	 * Return decision
	 * 
	 * @return decision
	 */
	public IJunctionDecision getDecision() {
		return this.decision;
	}

	/**
	 * Return whether this is a monitoring event
	 * 
	 * @return
	 */
	public boolean getMonitoring() {
		return this.doMonitor;
	}

	/**
	 * Visitor for handling events
	 */
	@Override
	public void handleEvent() throws Exception {
		this.target.handleEvent(this);
	}

	/**
	 * Set the decision
	 * 
	 * @param decision
	 * @return Self-Reference
	 */
	public DriverEvent setDecision(IJunctionDecision decision) {
		this.decision = decision;

		return this;
	}

	/**
	 * Set whether this is a monitoring event or not
	 * 
	 * @param doMonitor
	 * @return the Instance of this class
	 */
	public DriverEvent setMonitoring(boolean doMonitor) {
		this.doMonitor = doMonitor;

		return this;
	}
}
