package driver;

import environment.IPriority;

public interface IDirection {
	/**
	 * returns true if the trajectory crosses my trajectory false else
	 * 
	 * @param comingFrom
	 * @param goingTo
	 * @return bool
	 */
	boolean crossesMe (IDirection comingFrom, IDirection goingTo);

	void evaluateDir(IPriority priority);
	
	void evaluateFrom(IPriority priority);
	
	void evaluateTo(IPriority priority);
	
	IDirection returnSelf ();
}
