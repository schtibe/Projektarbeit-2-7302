package environment;

import java.util.List;

import car.CarCannotReverseException;
import car.IVehicle;
import car.VehicleFactory;
import driver.Character;
import driver.Physics;

public interface IGaia {
	public List<IRoad> getRoads();
	
	public List<IJunction> getJunctions();
	
	public List<IVehicle> getVehicles();
	
	public void setVehicle(IVehicle vehicle);
	
	public List<ITrafficCarrier> getTrafficCarriers();
	
	public IVehicle addRoadUser(
			VehicleFactory.VehicleType vehicleType, 
			ILane lane, 
			Character character, 
			Physics physics) throws Exception;
	
	public IVehicle addRoadUser(
			VehicleFactory.VehicleType vehicleType, 
			float drivenLaneDistance,
			ILane lane, 
			Character character, 
			Physics physics) throws Exception;
	
	public void updateVehicles(float timestep) 
			throws CarCannotReverseException;
	
	public void reset() throws Exception;
	
	public void destroy();
	
	public List<IWayPoint> getWayPoints();
}
