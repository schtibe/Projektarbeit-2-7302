package car;

import simulation.VehicleEvent;

import common.GlobalConstants;

import environment.ILane;

/**
 * The first implemented vehicle
 * 
 */
public class Car extends Vehicle {
	
	long lastStep; 
	
	/**
	 * Initialize the car's variables
	 * 
	 * @param lane
	 *            The lane to put the car on
	 */
	public Car(ILane lane) {
		super(lane);
		this.speed = 0;
		this.dimension = new VehicleDimension(1.8f * GlobalConstants
				.getInstance().getScale(), 3.5f * GlobalConstants.getInstance()
				.getScale());
		lastStep = 0;
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
		this.speed = 0f;

		this.dimension = new VehicleDimension(1.8f * GlobalConstants
				.getInstance().getScale(), 3.5f * GlobalConstants.getInstance()
				.getScale());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void adjustSpeed(float timestep) throws CarCannotReverseException {
		this.speed += this.acceleration * (timestep/1000);
		if (this.speed < 0) {
			this.speed = 0;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param VehilceEvent
	 */
	@Override
	public void handleEvent(VehicleEvent event) {
		if (lastStep == 0){
			lastStep = event.getTimeStamp();
			accelerate(event.getTargetAcceleration()); 
		}else{
			try{
				adjustSpeed (event.getTimeStamp()-lastStep);
			}catch (Exception ex){
				System.out.println("car tried to reverse");
			}
			lastStep = event.getTimeStamp();
			accelerate (event.getTargetAcceleration());
		}
	}
}
