package environment;

import java.util.List;

public interface ITrafficCarrier {
	/**
	 * return the right lanes
	 * 
	 * @return the rightLanes
	 */
	public List<ILane> getRightLanes();
	
	/**
	 * Return the left lanes
	 * @return the left lanes
	 */
	public List<ILane> getLeftLanes();
}
