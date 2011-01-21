package driver;

/**
 * Container for decision
 */
public interface IDecision {
	/**
	 * Return the decision ID
	 * @return Decision id
	 */
	int getID();
	
	/**
	 * Return the direction that the driver has decided to go
	 * @return Direction
	 */
	IDirection getDirection();
}
