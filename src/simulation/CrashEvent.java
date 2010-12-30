package simulation;

import car.Vehicle;

public class CrashEvent extends Event<CrashEvent> {
	
	Vehicle[] vehicles;
	
	/**
	 * Initialize
	 * 
	 * @param timestamp
	 * @param target
	 */
	public CrashEvent(long timestamp, IEventTarget<CrashEvent> target,Vehicle one, Vehicle other) {
		super(timestamp, target);
		vehicles = new Vehicle[]{one,other};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleEvent() throws Exception {
		this.target.handleEvent(this);
	}
	
	public Vehicle[] getVehicles(){
		return vehicles;
	}
}