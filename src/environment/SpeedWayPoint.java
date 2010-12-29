package environment;

import gui.IUIAdapterWayPoint;
import gui.UIElementFactory;

import common.IVector;

import driver.Animus;

public class SpeedWayPoint extends SignWayPoint {
	int speedLimit;
	
	IVector position;
	
	public SpeedWayPoint(ILane lane, int speedLimit, IVector position) {
		super(lane);
		this.speedLimit = speedLimit;
		this.position = position;
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
	public float getXPos(){
		return this.position.getComponent(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getYPos(){
		return this.position.getComponent(1);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws Exception 
	 */
	@Override
	public IUIAdapterWayPoint<SpeedWayPoint> visitUIFactory(float scale, IVector offsetVector) throws Exception {
		return UIElementFactory.getUIElement(this, scale, offsetVector);
	}
	
	@Override
	public String toString(){
		return "type: speed sign\ncoordinates: "+this.position.toString(); 
	}
}
