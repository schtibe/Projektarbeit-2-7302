package driver;

import java.util.ArrayList;
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
import environment.IPriority;
import environment.IWayPoint;
import environment.JunctionWayPoint;
import environment.PriorityRight;
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
	protected int targetSpeed = 50;
	protected float nearestVehicleDistance;
	protected float nearestVehicleDistanceOld;
	protected List<VehicleWayPoint> vehicleWayPoints;
	
	/**
	 * If we are driving through a junction, save its waypoint here
	 */
	protected JunctionWayPoint currentJunction = null;
	
	/**
	 * Indicated whether a vehicle has been seen or not
	 */
	protected boolean noVehicles;
	
	private Queue<IWayPoint> seenWayPoints;
	private IJunctionDecision decision;
	
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
		if (vehicle.isFreezed()){
			return;
		}
		
		this.forgetJunction();
		
		this.event = event;
		this.noVehicles = true;
		// the vehicle and the junction can only influence the deceleration
		DecelerationActivator vehicleActivator = new DecelerationActivator(0f);
		DecelerationActivator junctionActivator = new DecelerationActivator(0f);
		
		IDriverView dView = this.physics.getView(vehicle.getDriverView());
		List<IPlacable> wayPoints = WayPointManager.getInstance().findWayPoints(dView);
		vehicleWayPoints = new ArrayList<VehicleWayPoint>();
		for(IPlacable waypoint : wayPoints) {
			((IWayPoint)waypoint).visitHandleWayPoint(this);
		}
		
		if (isOnJunction()){
			List<ILane> lanes = this.currentJunction.getJunction().getRelevantLanes(this.vehicle.getLane());
			float nextWayPoint = Float.MAX_VALUE;
			for (VehicleWayPoint waypoint:vehicleWayPoints){
				List<ILane> wpLanes = new ArrayList<ILane>(waypoint.getVehicle().getLanes());
				for (ILane lane : wpLanes){
					if (lanes.contains(lane)){
						Vehicle vehicle = waypoint.getVehicle();
						if (this.decision != null){
							IDirection dir = this.decision.getDirection();
							IDirection from = this.currentJunction.getJunction().comingFrom(this.vehicle.getLane(), vehicle.getLane());
							IDirection to = vehicle.getSimpleDirection();
							if (dir.crossesMe(from,to)){
								IPriority priority = new PriorityRight();
								if (!priority.hasPriority(dir, from, to)){
									float distance = waypoint.getDistance(this.vehicle);
									if (distance < nextWayPoint){
										nextWayPoint = distance;
									}
								}
							}
							break;
						}
					}
				}
			}
			if (nextWayPoint < securityDistance()){
				junctionActivator.setValue(
						this.calculateVehicleActivator(
						securityDistance(),
						nextWayPoint,
						this.vehicle.getSpeed()
					)
				);
			}
		}
		
		if (this.noVehicles) {
			nearestVehicleDistanceOld = Float.MAX_VALUE;
			nearestVehicleDistance = Float.MAX_VALUE;
		}
		
		float securityDistance = this.securityDistance();
		if (nearestVehicleDistance < securityDistance) {
			vehicleActivator.setValue(
					/*
					this.calculateVehicleActivator(
							
					)*/
					this.calculateVehicleActivator(
							securityDistance(),
							nearestVehicleDistance,
							this.vehicle.getSpeed()
					)
			);
		}
		
		// save the distance of the nearest vehicle for the next assessment
		nearestVehicleDistanceOld = nearestVehicleDistance;
		
		float acceleration = 0;
		float speedAssessed = assessSpeeds(this.vehicle.getSpeed(),(float)targetSpeed);
		
		// calculate the acceleration depending on the activators
		if (this.vehicle.getSpeed() > (float)targetSpeed) {
			acceleration = generateAcceleration(
					new DecelerationActivator(speedAssessed), 
					vehicleActivator, 
					junctionActivator
			);
		} else {
			acceleration = generateAcceleration(
					new AccelerationActivator(speedAssessed),
					vehicleActivator,
					junctionActivator
			);
		}
		
		VehicleEvent evt = new VehicleEvent(
				event.getTimeStamp() + physics.getUpdateInterval(),
				vehicle,
				acceleration
		);
		EventQueue.getInstance().addEvent(evt);
	}

	/**
	 * Returns whether the speed should be changed to achieve the target speed
	 * @param vehicle
	 * @param target
	 * @return
	 */
	private float assessSpeeds(float vehicle, float target) {
		float percentage = Math.abs((vehicle/target)-1f);
		
		if (percentage < GlobalConstants.getInstance().getSpeedPerceptionThreshold()) {
			return 0;
		}
		
		if (percentage > GlobalConstants.getInstance().getAccelerationThreshold()) {
			return 1f;
		} else { 
			return (1f/GlobalConstants.getInstance().getAccelerationThreshold()) * percentage;
		}
	}

	/**
	 * Bring the activators together
	 * @param speedActivator
	 * @param vehicleActivator
	 * @param junctionActivator
	 * @return
	 */
	private float generateAcceleration(DecelerationActivator speedActivator,
			DecelerationActivator vehicleActivator,
			DecelerationActivator junctionActivator) {
		return calculateAcceleration (
				-character.changeActivator(vehicleActivator).getValue(), 
				-character.changeActivator(junctionActivator).getValue(),
				-character.changeActivator(speedActivator).getValue()
		);
	}
	
	/**
	 * Bring the activators together
	 * @param speedActivator
	 * @param vehicleActivator
	 * @param junctionActivator
	 * @return
	 */
	private float generateAcceleration(AccelerationActivator speedActivator,
			DecelerationActivator vehicleActivator,
			DecelerationActivator junctionActivator) {
		return calculateAcceleration (
				-character.changeActivator(vehicleActivator).getValue(), 
				-character.changeActivator(junctionActivator).getValue(),
				character.changeActivator(speedActivator).getValue()
		);
	}
	
	/**
	 * Calculate, how heavy the driver should step on the brakes
	 * 
	 * @param vehicleDistance The distance to the next vehicle
	 * @param speed The current speed
	 * @return
	 */
	private float calculateVehicleActivator(
			float securityDistance,
			float vehicleDistance, 
			float speed
		) {
		float brakeWay = (float) Math.pow((speed / 10), 2);
		float map = securityDistance - brakeWay;
		float dist = vehicleDistance - brakeWay;
		
		float res = 1 - (dist / map);
		
		return res;
	}
	
	/**
	 * Calculate the security distance
	 * @return
	 */
	private float securityDistance() {
		return this.vehicle.getSpeed() * 2;
	}

	/**
	 * 
	 * @param vehicle
	 * @param junction
	 * @param speed
	 * @return
	 */
	private float calculateAcceleration (float vehicle, float junction, float speed){
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
		
		return acceleration;
	}

	/**
	 * handles a speed way point
	 * @param waypoint
	 */
	public void handleWayPoint (SpeedWayPoint waypoint){
		if (vehicle.getLane().equals(waypoint.getLane())){
			if (waypoint.getDistance(vehicle) < this.securityDistance()) {
				targetSpeed = waypoint.getSpeedLimit();
			}
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
	 * handles a junction way point
	 * @param waypoint
	 */
	public void handleWayPoint (JunctionWayPoint waypoint) {
		this.rememberJunction(waypoint);
		if (this.vehicle.getLanes().size() < Vehicle.queueSize) {
 			if (this.checkWayPoint(vehicle, waypoint)) {
				List<IJunctionDecision> decisions = 
					waypoint.getJunction().getPossibilities(waypoint.getLane());
				IJunctionDecision decision = decisions.get((int)Math.round(Math.random()*(decisions.size()-1)));
				EventQueue.getInstance().addEvent(
						new DriverEvent(this.event.getTimeStamp(), 
						this.event.getTarget()).setDecision(decision));
				
				this.vehicle.notify("signal " + decision.getDirection().toString());
			}
		}
	}
	
	/**
	 * Remember a junction
	 * @param wp
	 */
	protected void rememberJunction(JunctionWayPoint wp) {
		this.currentJunction = wp;
	}
	
	/**
	 * We have finished driving through a junction, it
	 * should be forgotten. This method decides whether
	 * to do it or not and then acts accordingly
	 */
	protected void forgetJunction() {
		if (this.vehicle.getLanes().size() <= 1) {
			if (this.currentJunction != null) {
				this.currentJunction = null;
				this.vehicle.notify("signal off");
			}
		}
	}

	/**
	 * Whether we are on a junction or not
	 * @return
	 */
	protected boolean isOnJunction() {
		return this.currentJunction != null;
	}
	
	/**
	 * Handle a car
	 * @param carWayPoint
	 */
	public void handleWayPoint(VehicleWayPoint waypoint) {
		if (waypoint != (VehicleWayPoint)vehicle.getWayPoint()){
			vehicleWayPoints.add(waypoint);
			VehicleDimension myDim = this.vehicle.getDimension();
			if (this.vehicle.getLanes().contains(waypoint.getLane())){
				float distance = waypoint.getDistance(this.vehicle);
				if (nearestVehicleDistance > distance){
					nearestVehicleDistance = distance;	
				} else if (noVehicles) {
					// we are the first vehicle so set the distance to it
					nearestVehicleDistance = distance;
				}
				VehicleDimension otherDim = waypoint.getVehicle().getDimension();
				if (distance < myDim.getBoundingRadius()+otherDim.getBoundingRadius()){
					
					detectCollison(waypoint);
				}
			}
		}
		noVehicles = false;
	}

	/**
	 * @param waypoint
	 */
	private void detectCollison(VehicleWayPoint waypoint) {
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
	 * Do not use this for other waypoints than junction way points
	 * checks if a waypoint is important for this lane
	 * Only be interested in junction way points that lie on the
	 * current lane.
	 * @param vehicle
	 * @param waypoint
	 * @return
	 */
	protected boolean checkWayPoint(IVehicle vehicle, IWayPoint waypoint)  {
		/*for (ILane lane: vehicle.getLanes()) {*/
			if (vehicle.getLanes().peek() == waypoint.getLane()) { // only use the current lane
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
		//}
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

	public void setDecision(IJunctionDecision junctionDecision) {
		this.decision = junctionDecision;
		this.vehicle.setSimpleDirection(this.decision.getDirection());
	}
}
