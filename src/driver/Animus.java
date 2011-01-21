package driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 * The brain of the driver
 */
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
	protected float nextWaypointOld;
	protected List<VehicleWayPoint> vehicleWayPoints;
	protected Map<VehicleWayPoint,Boolean> crossingVehicles;
	
	/**
	 * If we are driving through a junction, save its waypoint here
	 */
	protected JunctionWayPoint currentJunction = null;
	
	/**
	 * Indicate whether a vehicle has been seen or not
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
		crossingVehicles = new HashMap<VehicleWayPoint,Boolean>();
	}
	
	/**
	 * Method that simulates the drivers mind
	 * 
	 * This method is the heart of the project. It collects the way points
	 * of the driver view and processes them, takes the decision.
	 * @param vehicle
	 * @param event
	 * @TODO refactor if you dare ;)
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
			float nextWaypoint = Float.MAX_VALUE;
			for (VehicleWayPoint waypoint:vehicleWayPoints){
				List<ILane> wpLanes = new ArrayList<ILane>(waypoint.getVehicle().getLanes());
				for (ILane lane : wpLanes){
					if (lanes.contains(lane)){
						Vehicle vehicle = waypoint.getVehicle();
						if (this.crossingVehicles.keySet().contains(waypoint)){
							if (this.crossingVehicles.get(waypoint)){
								System.out.println(this.vehicle.hashCode()+" knows already "+vehicle.hashCode());
								float distance = waypoint.getDistance(this.vehicle);
								if (distance < nextWaypoint){
									nextWaypoint = distance;
								}
							}
						}else{
							System.out.println(this.vehicle.hashCode()+" vehicle is on my lanes "+vehicle.hashCode());
							if (this.decision != null){
								IDirection dir = this.decision.getDirection();
								//TODO remember each car u saw that can cross your trail and handle that cars position before u evaluate
								//System.out.println("coming from: ");
								IDirection from = this.currentJunction.getJunction().comingFrom(this.vehicle.getLane(), vehicle.getLane());
								IDirection to = vehicle.getSimpleDirection();
								//System.out.println("d:"+dir.toString()+";f:"+from.toString()+";t:"+to.toString());
								if (dir.crossesMe(from,to)){
									this.crossingVehicles.put(waypoint, true);
									//System.out.println(this.vehicle.hashCode()+" has crossing "+vehicle.hashCode());
									IPriority priority = new PriorityRight();
									boolean prio = priority.hasPriority(dir, from, to);
									//System.out.println(prio);
									if (prio){
										System.out.println(vehicle.hashCode()+"has priority over"+this.vehicle.hashCode());
										float distance = waypoint.getDistance(this.vehicle);
										if (distance < nextWaypoint){
											nextWaypoint = distance;
										}
										this.crossingVehicles.put(waypoint, true);
									}else{
										this.crossingVehicles.put(waypoint, false);
									}
								}
								break;
							}
						}
					}
				}
			}
			if (nextWaypoint < securityDistance() && nextWaypoint < nextWaypointOld){
				junctionActivator.setValue(
						this.calculateVehicleActivator(
								securityDistance(),
								nextWaypoint,
								this.vehicle.getSpeed()
						)
				);
				nextWaypointOld = nextWaypoint;
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
		float speedAssessed = assessSpeeds(this.vehicle.getSpeed(),targetSpeed);
		
		// calculate the acceleration depending on the activators
		if (this.vehicle.getSpeed() > targetSpeed) {
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
	 * 
	 * @param vehicle
	 * @param target
	 * @return Decision about changing the speed
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
	 * 
	 * @param speedActivator
	 * @param vehicleActivator
	 * @param junctionActivator
	 * @return The calculated acceleration
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
	 * 
	 * @param speedActivator
	 * @param vehicleActivator
	 * @param junctionActivator
	 * @return The calculated acceleration
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
	 * @return The calculated activator value
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
	 * Calculate the acceleration from the activators
	 * 
	 * @param vehicle The activator value from the vehicle way points
	 * @param junction The activator value from the junction way points
	 * @param speed The activator value from the speed way points
	 * @return The resulting acceleration
	 */
	private float calculateAcceleration (float vehicle, float junction, float speed) {
		GlobalConstants constants = GlobalConstants.getInstance();
		float acceleration;
		float n;
		float weightN = constants.getJunctionWaypointInfluence()+constants.getVehicleWaypointInfluence();
		if (vehicle == 0 || junction == 0){
			n = vehicle+junction;
		} else {
			n = (vehicle*constants.getVehicleWaypointInfluence()+junction*constants.getJunctionWaypointInfluence())/
			(weightN); 
		}
		
		if (n == 0 || speed == 0) {
			acceleration = n+speed;
		} else {
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
				this.crossingVehicles = new HashMap<VehicleWayPoint,Boolean>();
				this.nextWaypointOld = Float.MAX_VALUE;
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
	 * Check if a rectangle intersects
	 * 
	 * @param length The length of the rectangle to check
	 * @param width The width of the rectangle to check
	 * @param corner The bottom left (?) corner of the rectangle
	 * @param center The center
	 * @return Wheter the rectangle intersect
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
	 * @return The driver physics
	 */
	public Physics getPhysics() {
		return this.physics;
	}
	
	/**
	 * get a reference on this character project
	 * @return The driver's character
	 */
	public Character getCharacter() {
		return this.character;
	}
	
	/**
	 * Check if a junction way point has already been seen
	 * 
	 * Do not use this for other way points than junction way points
	 * checks if a way point is important for this lane
	 * Only be interested in junction way points that lie on the
	 * current lane.
	 * 
	 * @param vehicle The vehicle for having the lanes
	 * @param waypoint The waypoint to check
	 */
	protected boolean checkWayPoint(IVehicle vehicle, IWayPoint waypoint)  {
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
					this.seenWayPoints.add(waypoint);
					return true;
				}
			}
		return false;
	}
	
	/**
	 * Remove the way points that are not on the lane
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

	/**
	 * Set the vehicle of the driver 
	 * @param vehicle
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		this.vehicle.register (this);
	}

	/**
	 * Set the decision a driver takes near a junction
	 * @param junctionDecision
	 */
	public void setDecision(IJunctionDecision junctionDecision) {
		this.decision = junctionDecision;
		this.vehicle.setSimpleDirection(this.decision.getDirection());
	}
}
