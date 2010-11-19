package car;

import java.util.List;
import java.util.Queue;

import simulation.IEventTarget;
import simulation.VehicleEvent;

import common.IVector;

import driver.Animus;
import driver.IDriverView;
import environment.ILane;
import environment.Lane;

/**
 * The Vehicle interface
 */
public interface IVehicle extends IEventTarget<VehicleEvent> {

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
	 * @param The
	 *            acceleration
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

	/**
	 * Handle an Event, please implement in subclasses
	 */
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
	 * @param list
	 *            of lanes
	 */
	void setLanes(List<ILane> lane);

	/**
	 * Set the animus
	 * 
	 * This was necessary, so the vehicle drive's around quickly. This is more a
	 * quick workaround, it should be solved in a better way, so the vehicle
	 * doesn't know the animus.
	 * 
	 * @param animus
	 */
	public void setAnimus(Animus animus);
}