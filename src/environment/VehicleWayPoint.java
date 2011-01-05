package environment;

import car.Vehicle;

public abstract class VehicleWayPoint extends MovingWayPoint {
	/**
	 * The vehicle that his way point belongs to
	 */
	protected Vehicle vehicle;
	
	/**
	 * Save the vehicle
	 * @param vehicle
	 */
	public VehicleWayPoint(Vehicle vehicle) {
		super(vehicle.getPosition());
		this.vehicle = vehicle;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Return the lane the vehicle is currently on
	 * @return Current lane
	 */
	@Override
	public ILane getLane() {
		return this.vehicle.getLane();
	}

	public Vehicle getVehicle (){
		return this.vehicle;
	}
}
