package environment;

import car.Vehicle;

import common.GlobalConstants;
import common.IVector;


public abstract class WayPoint implements IWayPoint {
	/**
	 * Return the fuzzy distance to the vehicle
	 * 
	 * This adds or subtracts some random error value based on a bias
	 * value
	 * @param vehicle
	 * @return
	 */
	public float getDistance(Vehicle vehicle) {
		IVector distanceVector = vehicle.getPosition().sub(this.getPosition());
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
	
	@Override
	public String toString(){
		try {
			return (int)this.getXPos()+"/" + (int)this.getYPos();
		} catch (Exception e) {
			return e.toString();
		}
	}
}
