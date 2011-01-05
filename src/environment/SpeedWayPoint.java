package environment;

import gui.IUIAdapterWayPoint;
import gui.UIElementFactory;

import common.IVector;

import driver.Animus;

public class SpeedWayPoint extends SignWayPoint {
	int speedLimit;
	
	public SpeedWayPoint(ILane lane, int speedLimit, IVector position) {
		super(lane, position);
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
	 * visitor method 
	 */
	@Override
	public void visitHandleWayPoint(Animus animus) {
		animus.handleWayPoint(this);
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
