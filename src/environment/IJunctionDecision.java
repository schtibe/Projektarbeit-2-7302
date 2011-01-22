package environment;

import java.util.List;

import driver.IDecision;

public interface IJunctionDecision extends IDecision {
	
	/**
	 * list of lanes for the decision
	 * @return list of lanes for the decision
	 */
	
	List<ILane> getLanes();
	
	/**
	 * get the junction for the decision
	 * @return get the junction for the decision
	 */
	
	IJunction getJunction();
}
