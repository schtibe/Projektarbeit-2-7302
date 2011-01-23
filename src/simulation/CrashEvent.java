package simulation;

import car.Vehicle;

/**
 * Event that indicates a crash
 */
public class CrashEvent extends Event<CrashEvent> {
	
	Vehicle[] vehicles;
	
	/**
	 * Initialise
	 * 
	 * @param timestamp
	 * @param target
	 */
	public CrashEvent(long timestamp, IEventTarget<CrashEvent> target,Vehicle one, Vehicle other) {
		super(timestamp, target);
		vehicles = new Vehicle[]{one,other};
	}

	@Override
	public void handleEvent() throws Exception {
		this.target.handleEvent(this);
	}
	
	/**
	 * Return the vehicles that have crashed
	 * @return Crashed vehicles
	 */
	public Vehicle[] getVehicles(){
		return vehicles;
	}
}