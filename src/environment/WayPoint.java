package environment;

public abstract class WayPoint implements IWayPoint {
	/**
	 * The lane the way point is on
	 */
	protected ILane lane;
	
	/**
	 * Construct the lange
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
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract float getXPos();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract float getYPos();
	
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
