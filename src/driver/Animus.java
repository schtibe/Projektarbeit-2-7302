package driver;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import simulation.DriverEvent;
import simulation.EventQueue;
import simulation.VehicleEvent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import car.IVehicle;
import car.Vehicle;
import environment.CarWayPoint;
import common.GlobalConstants;
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
	protected int targetSpeed = 30;
	
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
		DecelerationActivator vehicleActivator = new DecelerationActivator(0f);
		DecelerationActivator junctionActivator = new DecelerationActivator(0f);
		IDriverView dView = this.physics.getView(vehicle.getDriverView());
		List<IPlacable> wayPoints = WayPointManager.getInstance().findWayPoints(dView);
		//this.clearWayPoints();
		for(IPlacable waypoint : wayPoints){
			((IWayPoint)waypoint).visitHandleWayPoint(this);
		}
		float acceleration = 0;
		float speedAssessed = assessSpeeds(vehicle.getSpeed(),(float)targetSpeed);
		if (speedAssessed != 0){
			if (vehicle.getSpeed()>(float)targetSpeed){
				acceleration = generateAcceleration (new DecelerationActivator(speedAssessed), vehicleActivator, junctionActivator);
			}else{
				acceleration = generateAcceleration (new AccelerationActivator(speedAssessed), vehicleActivator, junctionActivator);
			}
		}
		VehicleEvent evt = new VehicleEvent(event.getTimeStamp()+physics.getUpdateInterval(),vehicle,acceleration);
		EventQueue.getInstance().addEvent(evt);
	}
	
	private float assessSpeeds(float vehicle, float target) {
		float percentage = Math.abs((vehicle/target)-1f);
		System.out.println("p:"+percentage);
		if (percentage<GlobalConstants.getInstance().getSpeedPerceptionThreshold()){
			return 0;
		}
		if (percentage > GlobalConstants.getInstance().getAccelerationThreshold()){
			return 1f;
		}else{
			return (1f/GlobalConstants.getInstance().getAccelerationThreshold())*percentage;
		}
	}

	private float generateAcceleration(DecelerationActivator speedActivator,
			DecelerationActivator vehicleActivator,
			DecelerationActivator junctionActivator) {
		return calculateAcceleration (
				-character.changeActivator(vehicleActivator).getValue(),
				-character.changeActivator(junctionActivator).getValue(),
				-character.changeActivator(speedActivator).getValue()
		);
	}
	
	private float generateAcceleration(AccelerationActivator speedActivator,
			DecelerationActivator vehicleActivator,
			DecelerationActivator junctionActivator) {
		return calculateAcceleration (
				-character.changeActivator(vehicleActivator).getValue(),
				-character.changeActivator(junctionActivator).getValue(),
				character.changeActivator(speedActivator).getValue()
		);
	}

	private float calculateAcceleration (float vehicle, float junction, float speed){
		System.out.println("speedActivator: "+speed);
		GlobalConstants constants = GlobalConstants.getInstance();
		float acceleration;
		float n;
		float weightN = constants.getJunctionWaypointInfluence()+constants.getVehicleWaypointInfluence();
		if (vehicle == 0 || junction == 0){
			n = vehicle+junction;
		}else{
			n = (vehicle*constants.getVehicleWaypointInfluence()+junction*constants.getJunctionWaypointInfluence())/
			(weightN); 
		}
		if (n == 0 || speed == 0){
			acceleration = n+speed;
		}else{
			acceleration = (n*weightN+speed*constants.getSpeedWaypointInfluence())/(weightN+constants.getSpeedWaypointInfluence());
		}
		System.out.println("acc:"+acceleration);
		return acceleration;
	}

	/**
	 * handles a speed waypoint
	 * @param waypoint
	 */
	public void handleWayPoint (SpeedWayPoint waypoint){
		if (vehicle.getLane().equals(waypoint.getLane())){
			System.out.println("handling speed wayPoint");
			System.out.println("original target speed: "+targetSpeed);
			System.out.println("car speed at this point:"+vehicle.getSpeed());
			System.out.println("new target speed: "+waypoint.getSpeedLimit());
			targetSpeed = waypoint.getSpeedLimit();
		}
	}
	
	/**
	 * handles a sign way point
	 * @param waypoint
	 */
	//public void handleWayPoint (VehicleWayPoint waypoint){
	//System.out.println("signy signal drawer");
	//}
	
	/**
	 * handles a sign way point
	 * @param waypoint
	 */
	public void handleWayPoint (SignWayPoint waypoint){
	//System.out.println("signy signal drawer");
	}
	
	/**
	 * handles a junction waypoint
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
						this.event.getTarget()).setDecision(decision));
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

	/**
	 * Handle a car
	 * @param carWayPoint
	 */
	public void handleWayPoint(CarWayPoint carWayPoint) {
		System.out.println("I Saw a car");
	}
}
