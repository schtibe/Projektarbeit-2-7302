package driver;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import simulation.DriverEvent;
import simulation.EventQueue;
import simulation.IEventTarget;
import simulation.VehicleEvent;
import car.IVehicle;
import car.Vehicle;
import environment.IJunctionDecision;
import environment.ILane;
import environment.IPlacable;
import environment.IWayPoint;
import environment.JunctionWayPoint;
import environment.SignWayPoint;
import environment.SpeedWayPoint;
import environment.WayPointManager;

public class Animus {
	
	/**
	 * instance variables
	 */
	protected Physics physics;
	protected Character character;
	protected IVehicle vehicle;
	protected DriverEvent event;
	protected int targetSpeed;
	
	private Queue<IWayPoint> seenWayPoints;
	
	/**
	 * constructor
	 * @param physics
	 * @param character
	 */
	public Animus (Physics physics, Character character){
		this.physics = physics;
		this.character = character;
		this.seenWayPoints = new ArrayBlockingQueue<IWayPoint>(10);
	}
	
	/**
	 * method that simulates the drivers mind
	 * @param vehicle
	 * @param event
	 * @throws Exception
	 */
	public void assessSituation (IVehicle vehicle, DriverEvent event) throws Exception{
		this.vehicle = vehicle;
		this.event = event;
		IDriverView dView = this.physics.getView(vehicle.getDriverView());
		List<IPlacable> wayPoints = WayPointManager.getInstance().findWayPoints(dView);
		//this.clearWayPoints();
		for(IPlacable waypoint : wayPoints){
			((IWayPoint)waypoint).visitHandleWayPoint(this);
		}
	}
	
	/**
	 * handles a speed waypoint
	 * @param waypoint
	 */
	public void handleWayPoint (SpeedWayPoint waypoint){
		System.out.println("handling speed wayPoint");
		System.out.println("original target speed: "+targetSpeed);
		System.out.println("car speed at this point:"+vehicle.getSpeed());
		System.out.println("new target speed: "+waypoint.getSpeedLimit());
		targetSpeed = waypoint.getSpeedLimit();
		if (vehicle.getSpeed() > (float)targetSpeed){
			VehicleEvent evt = new VehicleEvent(event.getTimeStamp()+physics.getUpdateInterval(),vehicle,-0.5f);
			EventQueue.getInstance().addEvent(evt);
		}
	}
	
	/**
	 * handles a sign way point
	 * @param waypoint
	 */
	public void handleWayPoint (SignWayPoint waypoint){
	//System.out.println("signy signal drawer");
	}
	
	/**
	 * handles any waypoint
	 * @param waypoint
	 */
	public void handleWayPoint (JunctionWayPoint waypoint) {
		if (this.vehicle.getLanes().size() < Vehicle.queueSize) {
 			if (this.checkWayPoint(vehicle, waypoint)) {
				List<IJunctionDecision> decisions = 
					waypoint.getJunction().getPossibilities(waypoint.getLane());
				IJunctionDecision decision = decisions.get((int)Math.round(Math.random()*(decisions.size()-1)));
				EventQueue.getInstance().addEvent(
						new DriverEvent(this.event.getTimeStamp(), 
						(IEventTarget<DriverEvent>)this.event.getTarget()).setDecision(decision));
			}
		}
	}
	
	/**
	 * get a reference on this physics object
	 * @return
	 */
	public Physics getPhysics() {
		return this.physics;
	}
	
	/**
	 * get a reference on this character project
	 * @return
	 */
	public Character getCharacter() {
		return this.character;
	}
	
	/**
	 * checks if a waypoint is important for this lane
	 * @param vehicle
	 * @param waypoint
	 * @return
	 */
	protected boolean checkWayPoint(IVehicle vehicle, IWayPoint waypoint)  {
		for (ILane lane: vehicle.getLanes()) {
			if (lane == waypoint.getLane()) {
				// test if it's already seen
				boolean seen = false;
				for (IWayPoint wp: this.seenWayPoints) {
					if (waypoint == wp) {
						seen = true;
						break;
					}
				}
				if (!seen) {
					try {
						if (waypoint.getXPos() > 141 && waypoint.getXPos() < 142) {
							System.out.println("debug");
						}
					} catch (Exception e) {}
					this.seenWayPoints.add(waypoint);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Remove the way points not on the lane
	 */
	public void laneChange(ILane lastLane) {
		for (IWayPoint wp: this.seenWayPoints) {
			if (wp.getLane() == lastLane) {
				this.seenWayPoints.remove(wp);
			}
		}
	}
}
