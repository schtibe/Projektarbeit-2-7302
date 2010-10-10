package car;

import simulation.VehicleEvent;

import common.GlobalConstants;

import environment.ILane;

/**
 * The first implemented vehicle
 *
 */
public class Car extends Vehicle {
	/**
	 * Initialize the car's variables
	 * 
	 * @param lane
	 *            The lane to put the car on
	 */
	public Car(ILane lane) {
		super(lane);
		this.speed = 60f;

		this.dimension = new VehicleDimension(1.8f * GlobalConstants
				.getInstance().getScale(), 3.5f * GlobalConstants.getInstance()
				.getScale());
	}

	/**
	 * Initialize the car's variables
	 * 
	 * @param lane
	 *            The lane to put the car on
	 * @param drivenLaneDistance
	 *            The distance on the lane to put the car on
	 */
	public Car(ILane lane, float drivenLaneDistance)
			throws IllegalArgumentException {
		super(lane, drivenLaneDistance);
		this.speed = 60f;

		this.dimension = new VehicleDimension(1.8f * GlobalConstants
				.getInstance().getScale(), 3.5f * GlobalConstants.getInstance()
				.getScale());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void adjustSpeed(float timestep) throws CarCannotReverseException {
		// TODO does this check belong here?
		if (this.speed == 0 && this.acceleration < 0) {
			throw new CarCannotReverseException();
		}

		this.speed += this.acceleration * timestep;
		if (this.speed < 0) {
			this.speed = 0;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This is currently not really implemented for testing reasons
	 */
	@Override
	public void handleEvent(VehicleEvent event) {
		// this.accelerate(event.getTargetAcceleration());
		this.speed = 60f;
	}
}
