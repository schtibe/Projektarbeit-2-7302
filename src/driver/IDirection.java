package driver;


/**
 * Indicates a direction relative to the personal position
 */
public interface IDirection {
	/**
	 * returns true if the trajectory crosses my trajectory false else
	 * 
	 * @param comingFrom The direction that the other object comes from
	 * @param goingTo The direction that the other object wants to take
	 * @return boolean Whether it crosses "my" direction
	 */
	boolean crossesMe (IDirection comingFrom, IDirection goingTo);
}
