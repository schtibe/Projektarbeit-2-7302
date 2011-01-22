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
	
	/**
	 * call back for visitor on direction
	 * @param dir
	 */
	
	void handleDir (IDirection dir);
	
	/**
	 * call back for visitor on direction
	 * @param dir
	 */
	
	void handleFrom (IDirection dir);
	
	/**
	 * call back for visitor on direction
	 * @param dir
	 */
	
	void handleTo (IDirection dir);
}
