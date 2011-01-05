package simulation;

import car.Vehicle;

import common.GlobalConstants;

import environment.Gaia;
import environment.WayPointManager;

public class Simulator implements IEventTarget<CrashEvent> {

	protected long startTime;
	protected long lastIteration;

	
	/**
	 * singleton instance
	 */
	private static Simulator instance;

	/**
	 * 
	 * method to access the singleton instance
	 * 
	 * @return singleton instance
	 */
	public synchronized static Simulator getInstance() {
		if (instance == null) {
			instance = new Simulator();
		}
		return instance;
	}
	
	private Simulator(){

	}

	public void startSimulation(long timestamp, double factor) {
		EventQueue.reset();
		this.startTime = timestamp;
		GlobalConstants.getInstance().setInitialTimestamp(timestamp);
		this.lastIteration = 0;
		GlobalConstants.getInstance().setTimeFactor(factor);
		
	}

	public synchronized void update(long timestamp) throws Exception {
		float difference = (timestamp - (this.startTime + this.lastIteration)); // Math.round((double)(timestamp-this.startTime)/GlobalConstants.getInstance().getTimeFactor());

		this.lastIteration += difference;
		Boolean goOn = true;
		Gaia.getInstance().updateVehicles(difference);
		while (goOn) {
			IEvent actualEvent = EventQueue.getInstance().getNextEvent(
					this.lastIteration);
			if (actualEvent != null) {
				actualEvent.handleEvent();
			} else {
				goOn = false;
			}
		}
	}
	
	public synchronized void handleEvent(CrashEvent event){
		System.out.println("a crash occured");
		for (Vehicle vehicle:event.getVehicles()){
			vehicle.freeze();
			try{
				/*
				System.out.println("----------------------------in there before crash:");
				for(IWayPoint waypoint: WayPointManager.getInstance().getWayPoints()){
					if (waypoint instanceof VehicleWayPoint){
						System.out.println(waypoint.toString());
					}
				}
				*/
				WayPointManager.getInstance().remove(vehicle.getWayPoint());
				vehicle.removeWaypoint();
				/*
				System.out.println("*****************************in there after crash:");
				for(IWayPoint waypoint: WayPointManager.getInstance().getWayPoints()){
					if (waypoint instanceof VehicleWayPoint){
						System.out.println(waypoint.toString());
					}
				}
				System.out.println("++++++++++++++++++++++++++++++in quad tree:");
				for (IPlacable placable : WayPointManager.getInstance().toList()){
					if (placable instanceof VehicleWayPoint){
						System.out.println(placable.toString());
					}
				}
				*/
			}catch (Exception ex){
				System.out.println(ex);
			}
		}
	}

}
