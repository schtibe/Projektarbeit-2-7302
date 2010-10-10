package simulation;

/**
 * Event for the vehicles
 */
public class VehicleEvent extends Event<VehicleEvent> {
	/**
	 * The acceleration to set on the vehicle
	 */
	private float targetAcceleration = 0;

	/**
	 * Initialize
	 * @param timestamp
	 * @param target
	 */
	public VehicleEvent(long timestamp, IEventTarget<VehicleEvent> target) {
		super(timestamp, target);
	}

	/**
	 * Initialize
	 * @param timestamp
	 * @param target
	 * @param targetAcceleration
	 */
	public VehicleEvent(long timestamp, IEventTarget<VehicleEvent> target,
			float targetAcceleration) {
		super(timestamp, target);

		this.targetAcceleration = targetAcceleration;
	}

	/**
	 * Set the target acceleration
	 * @param targetAcceleration The acceleration the car should use
	 * @return self-reference
	 */
	public VehicleEvent setTargetAcceleration(float targetAcceleration) {
		this.targetAcceleration = targetAcceleration;

		return this;
	}

	/**
	 * Return the target acceleration
	 * @return the target acceleration
	 */
	public float getTargetAcceleration() {
		return this.targetAcceleration;
	}

	/**
	 * Visitor to handle the event
	 */
	@Override
	public void handleEvent() throws Exception {
		this.target.handleEvent(this);
	}
}
