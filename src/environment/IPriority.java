package environment;

import driver.IDirection;

public interface IPriority {
	/**
	 * returns if the other car has priority over the own car
	 * @param dir
	 * @param from
	 * @param to
	 * @return true if other has priority false else
	 */
	
	boolean hasPriority (IDirection dir, IDirection from, IDirection to);
	
	void handleDir (IDirection dir);
	
	void handleFrom (IDirection dir);
	
	void handleTo (IDirection dir);
}
