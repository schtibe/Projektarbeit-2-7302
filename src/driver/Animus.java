package driver;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import simulation.CrashEvent;
import simulation.DriverEvent;
import simulation.EventQueue;
import simulation.Simulator;
import simulation.VehicleEvent;
import car.IVehicle;
import car.Vehicle;
import car.VehicleDimension;
import common.GlobalConstants;
import common.IObserver;
import common.IVector;
import common.LinearCombination;
import common.Vector;
import environment.IJunctionDecision;
import environment.ILane;
import environment.IPlacable;
import environment.IWayPoint;
import environment.JunctionWayPoint;
import environment.SignWayPoint;
import environment.SpeedWayPoint;
import environment.VehicleWayPoint;
import environment.WayPointManager;

public class Animus implements IObserver {
	
	/**
	 * instance variables
	 */
	protected Physics physics;
	protected Character character;
	protected Vehicle vehicle;
	protected DriverEvent event;
	protected int targetSpeed = 30;
	protected float nearestVehicleDistance;
	protected float nearestVehicleDistanceOld;
	protected boolean noVehicles;
	
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
		nearestVehicleDistance = Float.MAX_VALUE;
		nearestVehicleDistanceOld = Float.MAX_VALUE;
	}
	
	/**
	 * method that simulates the drivers mind
	 * @param vehicle
	 * @param event
	 * @throws Exception
	 */
	public void assessSituation (DriverEvent event) throws Exception{
		this.event = event;
		noVehicles = true;
		DecelerationActivator vehicleActivator = new DecelerationActivator(0f);
		DecelerationActivator junctionActivator = new DecelerationActivator(0f);
		IDriverView dView = this.physics.getView(vehicle.getDriverView());
		List<IPlacable> wayPoints = WayPointManager.getInstance().findWayPoints(dView);
		for(IPlacable waypoint : wayPoints){
			((IWayPoint)waypoint).visitHandleWayPoint(this);
		}
		if (noVehicles){
			nearestVehicleDistanceOld = Float.MAX_VALUE;
			nearestVehicleDistance = Float.MAX_VALUE;
		}
		if (nearestVehicleDistanceOld != Float.MAX_VALUE){
			//if (nearestVehicleDistanceOld > nearestVehicleDistance){
			//	vehicleActivator.setValue(1.0f);
			//}
			//this.vehicle.getSpeed()/2;
		}
		float securityDistance = 200f;
		if (nearestVehicleDistance < securityDistance){
			vehicleActivator.setValue(1.0f);
		}
		nearestVehicleDistanceOld = nearestVehicleDistance;
		float acceleration = 0;
		float speedAssessed = assessSpeeds(vehicle.getSpeed(),(float)targetSpeed);
		if (vehicle.getSpeed()>(float)targetSpeed){
			acceleration = generateAcceleration (new DecelerationActivator(speedAssessed), vehicleActivator, junctionActivator);
		}else{
			acceleration = generateAcceleration (new AccelerationActivator(speedAssessed), vehicleActivator, junctionActivator);
		}
		VehicleEvent evt = new VehicleEvent(event.getTimeStamp()+physics.getUpdateInterval(),vehicle,acceleration);
		EventQueue.getInstance().addEvent(evt);
	}
	
	private float assessSpeeds(float vehicle, float target) {
		float percentage = Math.abs((vehicle/target)-1f);
		//System.out.println("p:"+percentage);
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
		//System.out.println("("+this.vehicle.hashCode()+") v:"+vehicle+" s: "+speed);
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
		//System.out.println("acc:"+acceleration);
		return acceleration;
	}

	/**
	 * handles a speed way point
	 * @param waypoint
	 */
	public void handleWayPoint (SpeedWayPoint waypoint){
		if (vehicle.getLane().equals(waypoint.getLane())){
			//System.out.println("handling speed wayPoint");
			//System.out.println("original target speed: "+targetSpeed);
			//System.out.println("car speed at this point:"+vehicle.getSpeed());
			//System.out.println("new target speed: "+waypoint.getSpeedLimit());
			targetSpeed = waypoint.getSpeedLimit();
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
	 * handles a junction waypoint
	 * @param waypoint
	 */
	public void handleWayPoint (JunctionWayPoint waypoint) {
		if (this.vehicle.getLanes().size() < Vehicle.queueSize) {
			//System.out.println("handling junction waypoint");
 			if (this.checkWayPoint(vehicle, waypoint)) {
 				//System.out.println("decision making for direction");
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
	 * Handle a car
	 * @param carWayPoint
	 */
	public void handleWayPoint(VehicleWayPoint waypoint) {
		noVehicles = false;
		if (waypoint != (VehicleWayPoint)vehicle.getWayPoint()){
			VehicleDimension myDim = this.vehicle.getDimension();
			if (this.vehicle.getLanes().contains(waypoint.getLane())){
				float distance = waypoint.getDistance(this.vehicle);
				if (nearestVehicleDistance > distance){
					nearestVehicleDistance = distance;
					
				}
				VehicleDimension otherDim = waypoint.getVehicle().getDimension();
				if (distance < myDim.getBoundingRadius()+otherDim.getBoundingRadius()){
					
					detectCollison(waypoint);
				}
				//System.out.println("("+this.vehicle.hashCode()+") now that bastard bothers me ("+distance+":"+nearestVehicleDistance+")");
			}
			//System.out.println("("+this.vehicle.hashCode()+") I saw a vehicle");
		}
	}

	/**
	 * @param waypoint
	 */
	private void detectCollison(VehicleWayPoint waypoint) {
		System.out.println("("+this.vehicle.hashCode()+") possible crash with ("+waypoint.getVehicle().hashCode()+")");
		
		IVector LengthA = this.vehicle.getDirection().normalize().multiply(vehicle.getDimension().getLength());
		IVector WidthA = this.vehicle.getDirection().normalize().multiply(vehicle.getDimension().getWidth()).rotate((float)Math.PI/2);
		
		IVector LengthB = waypoint.getVehicle().getDirection().normalize().multiply(vehicle.getDimension().getLength());
		IVector WidthB = waypoint.getVehicle().getDirection().normalize().multiply(vehicle.getDimension().getWidth()).rotate((float)Math.PI/2);
		
		IVector lowerLeftA = vehicle.getPosition().sub(LengthA.multiply(0.5f)).sub(WidthA.multiply(0.5f));
		IVector lowerLeftB = waypoint.getVehicle().getPosition().sub(LengthB.multiply(0.5f)).sub(WidthB.multiply(0.5f));
		
		boolean testedA = checkInside(LengthA, WidthA, lowerLeftA,vehicle.getPosition());
		
		boolean testedB = checkInside(LengthB, WidthB, lowerLeftB,waypoint.getVehicle().getPosition());
		
		if (testedB || testedA){
			CrashEvent crash = new CrashEvent(
					1,
					Simulator.getInstance(),
					this.vehicle,
					waypoint.getVehicle());
			EventQueue.getInstance().addEvent(crash);
		}
	}

	/**
	 * @param length
	 * @param width
	 * @param corner
	 * @param center
	 * @return
	 */
	private boolean checkInside(IVector length, IVector width,
			IVector corner, IVector center) {
		IVector[] toTest = new IVector[4];
		
		toTest[0] = corner;
		toTest[1] = corner.add(length);
		toTest[2] = corner.add(width);
		toTest[3] = toTest[2].add(length);

		for (IVector testV : toTest){
			LinearCombination comb = Vector.getLinearCombination (center,length,width,testV);
			if (
				comb.getMu() > 0 &&
				comb.getMu() < 1 &&
				comb.getLambda() > 0 &&
				comb.getLambda() < 1
			){
				return true;
			}
		}
		return false;
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
	private void laneChange(ILane lastLane) {
		for (IWayPoint wp: this.seenWayPoints) {
			if (wp.getLane() == lastLane) {
				this.seenWayPoints.remove(wp);
			}
		}
	}

	@Override
	public void update(String message) {
		if (message.compareTo("laneChange")== 0){
			//System.out.println(message);
			Queue<ILane> lanes = vehicle.getLanes();
			this.laneChange(lanes.poll());
		}
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		this.vehicle.register (this);
	}
}
