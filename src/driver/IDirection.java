package driver;

public interface IDirection {
	/**
	 * returns true if the trajectory crosses my trajectory false else
	 * 
	 * @param comingFrom
	 * @param goingTo
	 * @return bool
	 */
	boolean crossesMe (IDirection comingFrom, IDirection goingTo);
}
