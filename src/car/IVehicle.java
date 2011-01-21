package car;

import java.util.List;
import java.util.Queue;

import simulation.IEventTarget;
import simulation.VehicleEvent;

import common.IObservable;
import common.IVector;

import driver.IDirection;
import driver.IDriverView;
import environment.ILane;
import environment.IMovable;
import environment.Lane;

/**
 * The Vehicle interface
 */
public interface IVehicle extends IEventTarget<VehicleEvent>, IObservable {

	/**
	 * Return the vehicle's dimensions
	 * 
	 * @return the vehicle's dimension
	 */
	public abstract VehicleDimension getDimension();

	/**
	 * Set the vehicle's dimensions
	 * 
	 * @param dimensions
	 */
	public abstract void setDimension(VehicleDimension dimension);

	/**
	 * Return the vehicle's speed
	 * 
	 * @return VehicleSpeed
	 */
	public abstract float getSpeed();

	/**
	 * Change the speed
	 * 
	 * @param The acceleration
	 */
	public abstract void accelerate(float acceleration);

	/**
	 * Return the lane the vehicle is on
	 * 
	 * @return The lane the vehicle is on
	 */
	public abstract ILane getLane();

	/**
	 * Return the lanes queue
	 * 
	 * @return
	 */
	public Queue<ILane> getLanes();

	/**
	 * Set one lane (added to the queue)
	 * 
	 * @param lane
	 */
	public abstract void setLane(ILane lane);

	/**
	 * Return the vehicle's position
	 * 
	 * @return
	 */
	public abstract IVector getPosition();

	/**
	 * Calculate the new position of the vehicle
	 * 
	 * - Adjust the speed - Get the new driven lane distance - Save the position
	 * which is calculated by the lane (see
	 * {@link Lane#getPositionOnLane(float)})
	 */
	public abstract void updatePosition(float timestep)
			throws CarCannotReverseException;

	/**
	 * Return the driven lane distance
	 * 
	 * @return
	 */
	public abstract float getDrivenLaneDistance();

	@Override
	public abstract void handleEvent(VehicleEvent event);

	/**
	 * Get the direction and the position of the vehicle in a DriverView Object
	 * 
	 * @return DriverView populated with vehicle position and direction
	 */
	public abstract IDriverView getDriverView();

	/**
	 * Get the alignment of the Vehicle
	 * 
	 * @return the direction according to the road
	 */
	public IVector getDirection();

	/**
	 * Set a list of lanes to the queue
	 * 
	 * @param list of lanes
	 */
	void setLanes(List<ILane> lane);
	
	/**
	 * Indicate whether the vehicle is frozen or not
	 * @return
	 */
	boolean isFreezed();
	
	/**
	 * Remove the way point
	 */
	void removeWaypoint();
	
	/**
	 * Create a way point from this vehicle
	 */
	public void createWayPoint();
	
	/**
	 * Return the way point of this vehicle
	 * 
	 * @return way point
	 */
	public abstract IMovable getWayPoint();
	
	/**
	 * Set the direction object
	 * @param dir Direction object
	 */
	public void setSimpleDirection(IDirection dir);
	
	/**
	 * Get the saved direction
	 * @return Direction
	 */
	public IDirection getSimpleDirection();
}