package environment;


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
	
	@Override
	public ILane getLane() {
		return lane;
	}
	
	@Override
	public String toString(){
		try {
			return (int)this.getXPos()+"/" + (int)this.getYPos();
		} catch (Exception e) {
			return e.toString();
		}
	}
}
