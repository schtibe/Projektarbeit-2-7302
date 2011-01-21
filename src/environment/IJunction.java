package environment;

import java.util.List;

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
	 * returns the list of all the other lanes running through the junction
	 * @param actualLane
	 * @return List<ILane>
	 */
	
	List<ILane> getRelevantLanes (ILane actualLane);

}
