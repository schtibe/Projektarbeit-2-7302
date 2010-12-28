package environment;

public interface IPlacableManager {
	
	/**
	 * add another item to the collection
	 * @param placable
	 * @throws Exception
	 */
	
	public void add(IPlacable placable) throws Exception;
	
	/**
	 * remove an item from the collection
	 * @param placable
	 * @throws Exception
	 */
	
	public void remove(IPlacable placable) throws Exception;
	
	/**
	 * move, geometrically, the IPlacable at positon oldX , oldY
	 * @param placable
	 * @param oldX
	 * @param oldY
	 */
	
	public boolean move(IMovable movable,float newX, float newY);
}
