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
	 * Initialise
	 * @param timestamp The time the event occurred
	 * @param target The target of the event
	 */
	public VehicleEvent(long timestamp, IEventTarget<VehicleEvent> target) {
		super(timestamp, target);
	}

	/**
	 * Initialise
	 * @param timestamp The time the event occurred
	 * @param target The target of the event
	 * @param targetAcceleration The target acceleration the car should use
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

	@Override
	public void handleEvent() throws Exception {
		this.target.handleEvent(this);
	}
}
