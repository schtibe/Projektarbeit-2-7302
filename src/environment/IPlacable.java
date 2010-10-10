package environment;

public interface IPlacable {
	
	/**
	 * the actual x coordinate of the IPlacable element
	 * @return float
	 * @throws Exception
	 */
	
	float getXPos() throws Exception;
	
	/**
	 * the actual y coordinate of the IPlacable element
	 * @return
	 * @throws Exception
	 */
	
	float getYPos() throws Exception;
}
