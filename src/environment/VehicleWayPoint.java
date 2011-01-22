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
	
	@Override
	public ILane getLane() {
		return this.vehicle.getLane();
	}
	
	/**
	 * get the vehicle for this waypoint
	 * @return the vehicle for this waypoint
	 */
	
	public Vehicle getVehicle (){
		return this.vehicle;
	}
}
