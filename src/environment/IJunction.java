package environment;

import java.util.List;

import driver.IDecision;
import driver.IDirection;

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
	 * get the direction of the other vehicle according to which lane I am on
	 * @param actualLane
	 * @param otherLane
	 * @return
	 */
	
	IDirection comingFrom (ILane actualLane, ILane otherLane);
	
	
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
