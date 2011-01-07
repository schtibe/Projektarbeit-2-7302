package environment;

import driver.IDirection;

public interface IPriority {
	boolean hasPriority (IDirection dir, IDirection from, IDirection to);
	
	void handleDir (IDirection dir);
	
	void handleFrom (IDirection dir);
	
	void handleTo (IDirection dir);
}
