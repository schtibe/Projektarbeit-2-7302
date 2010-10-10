package environment;

import driver.Animus;

public class SpeedWayPoint extends SignWayPoint {
	int speedLimit;
	
	public SpeedWayPoint(ILane lane, int speedLimit) {
		super(lane);
		this.speedLimit = speedLimit;
	}

	/**
	 * get the speed limit 
	 * @return speed limit
	 */
	
	public int getSpeedLimit() {
		return this.speedLimit;
	}

	/**
	 * compare two way points
	 */
	
	@Override
	public int compareTo(IWayPoint o) {
		// todo, maybe
		return 0;
	}

	/**
	 * visitor method 
	 */
	
	@Override
	public void visitHandleWayPoint(Animus animus) {
		animus.handleWayPoint(this);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public float getXPos() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public float getYPos() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
