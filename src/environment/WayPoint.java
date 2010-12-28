package environment;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class WayPoint implements IWayPoint {
	/**
	 * The lane the way point is on
	 */
	protected ILane lane;
	
	/**
	 * Construct the lane
	 * @param lane
	 */
	public WayPoint (ILane lane){
		this.lane = lane;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ILane getLane() {
		return lane;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(IWayPoint o) {
		throw new NotImplementedException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){
		try {
			return (int)this.getXPos()+"/" + (int)this.getYPos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.toString();
		}
	}
}
