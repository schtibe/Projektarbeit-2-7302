package environment;

import java.util.List;

import driver.IDecision;

public interface IJunction extends ITrafficCarrier{
	
	/**
	 * set the roads that go together in this junction
	 * @param roads
	 * @throws Exception
	 */
	
	void setRoads (List<IRoad> roads) throws Exception;
	
	/**
	 * get the next possible lanes
	 * @param actualLane
	 * @return
	 */
	
	List<IJunctionDecision> getPossibilities(ILane actualLane);
	
	/**
	 * {@deprecated}
	 * @param decision
	 * @return
	 */
	
	@Deprecated
	List<ILane> getImportantLanes(IDecision decision);
	//List<IWayPoint> getWayPoints(Lane actualLane);
	//List<ILane> getJunctionLanes();
	
	//public void handleVehicle(IVehicle vehicle);
}
