package environment;

import java.util.List;

import driver.IDecision;

public interface IJunctionDecision extends IDecision {
	List<ILane> getLanes();
	IJunction getJunction();
}
