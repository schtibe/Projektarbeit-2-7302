package environment;

import common.GlobalConstants;
import common.IVector;

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
	 * Return the fuzzy distance to the vehicle
	 * 
	 * This adds or subtracts some random error value based on a bias
	 * value
	 * @param vehicle
	 * @return
	 */
	public float getDistance(Vehicle vehicle) {
		IVector distanceVector = vehicle.getPosition().sub(this.position);
		float actualLength = distanceVector.norm();
		
		float bias = GlobalConstants.getInstance().getDistanceFuzzyBias();
		float maxError = actualLength * bias;
		
		float rand = (float) Math.random();
		float error = rand * maxError;
		if (rand > 0.5) {
			return actualLength + error;
		} else {
			return actualLength - error;
		}
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

}
