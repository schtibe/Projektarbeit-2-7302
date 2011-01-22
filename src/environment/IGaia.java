package environment;

import java.util.List;

import car.CarCannotReverseException;
import car.IVehicle;
import car.VehicleFactory;
import driver.Character;
import driver.Physics;

public interface IGaia {
	
	/**
	 * a list of all the roads
	 * @return a list of all the roads
	 */
	
	public List<IRoad> getRoads();
	
	/**
	 * a list of all the junctions
	 * @return a list of all the junctions
	 */
	
	public List<IJunction> getJunctions();
	
	/**
	 * a list of all the vehicles
	 * @return a list of all the vehicles
	 */
	
	public List<IVehicle> getVehicles();
	
	/**
	 * add a vehicle to the world
	 * @param vehicle
	 */
	
	public void setVehicle(IVehicle vehicle);
	
	/**
	 * get a list of all traffic carriers (roads and junctions)
	 */
	
	
	public List<ITrafficCarrier> getTrafficCarriers();
	
	/**
	 * Add a road user to the simulation
	 * 
	 * Create a new car and a driver with the given properties
	 * and put the whole thing into action
	 * @param vehicleType The type of the vehicle
	 * @param lane The lane to put the vehicle on
	 * @param character The character properties of the driver
	 * @param physics The physics of the driver
	 * @throws Exception
	 */
	
	public IVehicle addRoadUser(
			VehicleFactory.VehicleType vehicleType, 
			ILane lane, 
			Character character, 
			Physics physics) throws Exception;
	
	/**
	 * Add a road user to the simulation
	 * 
	 * Create a new car and a driver with the given properties
	 * and put the whole thing into action
	 * @param vehicleType The type of the vehicle
	 * @param drivenLaneDistance The distance on the lane the vehicle has already driven
	 * @param lane The lane to put the vehicle on
	 * @param character The character properties of the driver
	 * @param physics The physics of the driver
	 * @throws Exception
	 */
	
	public IVehicle addRoadUser(
			VehicleFactory.VehicleType vehicleType, 
			float drivenLaneDistance,
			ILane lane, 
			Character character, 
			Physics physics) throws Exception;
	
	/**
	 * initiates the vehicle updates
	 * @param timestep, time elapsed since last update
	 * @throws CarCannotReverseException
	 */
	
	public void updateVehicles(float timestep) 
			throws CarCannotReverseException;
	
	/**
	 * destroy the actual gaia instance
	 */
	
	public void destroy();
	
	/**
	 * get a list of the waypints in the world
	 * @return all the waypoints in the world
	 */
	
	public List<IWayPoint> getWayPoints();
}
