package car;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import simulation.VehicleEvent;

import common.GlobalConstants;
import common.IVector;
import common.Vector;

import driver.Animus;
import driver.DriverView;
import driver.IDriverView;
import environment.ILane;
import environment.LaneLengthExceededException;

/**
 * The abstract vehicle
 * 
 * This class contains all the needed information and methods for a vehicle.
 * 
 */
public abstract class Vehicle implements IVehicle {
	/**
	 * This is more like a temporary hack
	 */
	protected Animus animus;

	/**
	 * The direction the vehicle is heading
	 * 
	 * This vector has always the length 1 (unit vector)
	 */
	protected IVector direction;

	/**
	 * The last direction
	 */
	protected IVector lastPosition;

	/**
	 * The current speed of the vehicle
	 */
	protected float speed = 0;

	/**
	 * The position in the simulation of the vehicle
	 */
	protected IVector position;

	public final static int queueSize = 12;

	/**
	 * The vehicle's dimension
	 */
	protected VehicleDimension dimension;

	/**
	 * {@inheritDoc}
	 */
	public VehicleDimension getDimension() {
		return dimension;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDimension(VehicleDimension dimension) {
		this.dimension = dimension;
	}

	/**
	 * The current acceleration
	 * 
	 * This acceleration is added to the speed and/or direction
	 */
	protected float acceleration;

	/**
	 * The lane the vehicle is on
	 */
	protected Queue<ILane> lanes = new ArrayBlockingQueue<ILane>(
			Vehicle.queueSize);

	/**
	 * The driven distance on the lane
	 */
	protected float drivenLaneDistance = 0;

	/**
	 * {@inheritDoc}
	 */
	public float getSpeed() {
		return this.speed;
	}

	/**
	 * Construct
	 * 
	 * @param lane
	 *            The lane the vehicle is on
	 */
	public Vehicle(ILane lane) throws IllegalArgumentException {
		if (lane == null) {
			throw new IllegalArgumentException();
		}

		this.construct(lane, 0);
	}

	/**
	 * Construct
	 * 
	 * Create a car and position it on a lane
	 * 
	 * @param lane
	 *            The lane the car is on
	 * @param drivenLaneDistance
	 *            The position on the lane
	 */
	public Vehicle(ILane lane, float drivenLaneDistance)
			throws IllegalArgumentException {
		if (lane == null) {
			throw new IllegalArgumentException();
		}

		this.construct(lane, drivenLaneDistance);
	}

	/**
	 * Initialize all needed variables
	 * 
	 * @param lane
	 * @param drivenLaneDistance
	 */
	private void construct(ILane lane, float drivenLaneDistance) {
		this.lanes.add(lane);
		this.drivenLaneDistance = drivenLaneDistance;
		try {
			this.position = this.lanes.peek().getPositionOnLane(
					this.drivenLaneDistance);
		} catch (LaneLengthExceededException e) {
			try {
				this.position = this.lanes.peek().getPositionOnLane(0);
			} catch (LaneLengthExceededException e1) {
				e1.printStackTrace();
			}
		}
		this.initializeDirection();
	}

	/**
	 * Set the direction upon initialization
	 */
	private void initializeDirection() {
		this.direction = this.lanes.peek().getFirstILaneSegment().getEndPoint()
				.sub(this.lanes.peek().getFirstILaneSegment().getStartPoint());
		this.lastPosition = this.lanes.peek().getFirstILaneSegment()
				.getStartPoint();
	}

	/**
	 * {@inheritDoc}
	 */
	public void accelerate(float acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * {@inheritDoc}
	 */
	public ILane getLane() {
		return this.lanes.peek();
	}

	/**
	 * {@inheritDoc}
	 */
	public IVector getPosition() {
		return new Vector(new float[] { this.position.getComponent(0),
				this.position.getComponent(1) });
	}

	/**
	 * {@inheritDoc}
	 */
	public void updatePosition(float timestep) throws CarCannotReverseException {
		this.adjustSpeed(timestep);
		this.drivenLaneDistance += (this.speed / 36)
				* GlobalConstants.getInstance().getScale();
		try {
			this.position = this.lanes.peek().getPositionOnLane(
					this.drivenLaneDistance);
		} catch (LaneLengthExceededException e) {
			// change lane
			this.drivenLaneDistance = this.drivenLaneDistance
					- this.lanes.peek().getLength();
			this.animus.laneChange(this.lanes.poll());
			try {
				this.position = this.lanes.peek().getPositionOnLane(
						this.drivenLaneDistance);
			} catch (LaneLengthExceededException e1) {
			} catch (NullPointerException e2) {
				System.out.println("No further lane found!");
				System.out.println("The car is " + this);
				System.out.println("The car was on position " + this.position);
				return;
			}
		} catch (NullPointerException e2) {
			System.out.println("No further lane found!");
			System.out.println("The car is " + this);
			System.out.println("The car was on position " + this.position);
			return;
		}

		this.updateDirection();
	}

	/**
	 * Update the direction of the car
	 * 
	 * The difference vector of the old and new position is the direction the
	 * car is looking
	 */
	public void updateDirection() {
		if (this.position.compareTo(this.lastPosition) == 0) {
			this.direction = this.lanes.peek().getFirstILaneSegment()
					.getEndPoint().sub(
							this.lanes.peek().getFirstILaneSegment()
									.getStartPoint());
		} else {
			this.direction = this.position.sub(this.lastPosition);
			this.lastPosition = this.position;
		}
	}

	/**
	 * Return the driven lane distance
	 * 
	 * @return Driven lane distance
	 */
	public float getDrivenLaneDistance() {
		return this.drivenLaneDistance;
	}

	/**
	 * Adjust the speed
	 * 
	 * @throws CarCannotReverseException
	 */
	protected abstract void adjustSpeed(float timestep)
			throws CarCannotReverseException;

	/**
	 * Handle an event
	 * 
	 * @see car.IVehicle#handleEvent(simulation.IEvent)
	 */
	public abstract void handleEvent(VehicleEvent event);

	@Override
	public void setLane(ILane lane) {
		this.lanes.add(lane);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLanes(List<ILane> lanes) {
		this.lanes.addAll(lanes);
	}

	/**
	 * {@inheritDoc}
	 */
	public Queue<ILane> getLanes() {
		return this.lanes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector getDirection() {
		return this.direction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IDriverView getDriverView() {
		return new DriverView(this.direction.normalize(), this.position);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAnimus(Animus animus) {
		this.animus = animus;
	}
}
