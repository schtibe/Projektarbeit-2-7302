package driver;

import simulation.DriverEvent;
import simulation.EventQueue;
import car.IVehicle;
import car.Vehicle;
import environment.IJunctionDecision;

public class Driver implements IDriver {
	
	protected IVehicle vehicle;
	protected Animus animus;
	
	protected IJunctionDecision junctionDecision = null;
	
	@Deprecated
	public Driver (IVehicle vehicle){
		this.vehicle = vehicle;
	}
	
	public Driver (IVehicle vehicle, Character character, Physics physics){
		this.vehicle = vehicle;
		this.animus = new Animus (physics, character);
		this.animus.setVehicle ((Vehicle) vehicle);
	}
	
	/**
	 * get the drivers animus
	 * @return
	 */
	
	public Animus getAnimus() {
		return this.animus;
	}
	
	/* (non-Javadoc)
	 * @see driver.IDriver#handleEvent(simulation.DriverEvent)
	 */
	
	/**
	 * handles a driver event
	 */
	
	@Override
	public void handleEvent(DriverEvent event) throws Exception{
	
		if (event.getMonitoring()) {
			//DriverView carView = this.vehicle.getDriverView();
			this.animus.assessSituation(event);
			
			EventQueue.getInstance().addEvent(new DriverEvent(
					event.getTimeStamp()+this.animus.getPhysics().getUpdateInterval(),
					this
			).setMonitoring(true));
		}
		
		if (event.getDecision() != null) {
			this.junctionDecision = event.getDecision();
			System.out.println("i am turning: "+event.getDecision().getDirection().toString());
			try {
				this.vehicle.setLanes(event.getDecision().getLanes());
			} catch (IllegalStateException e) {}
		}
	}
}
