package environment;

import gui.IUIAdapterWayPoint;

import common.IVector;

import driver.Animus;

public abstract class StaticWayPoint extends WayPoint {
	
	protected IVector position;
	
	/**
	 * The lane the way point is on
	 */
	protected ILane lane;
	
	public StaticWayPoint(ILane lane, IVector position) {
		this.lane = lane;
		this.position = position;
	}
	
	@Override
	public ILane getLane() {
		return lane;
	}
	

	@Override
	public void visitHandleWayPoint(Animus animus) {
		// TODO Auto-generated method stub

	}

	@Override
	public IUIAdapterWayPoint<?> visitUIFactory(float scale,
			IVector offsetVector) throws Exception {
		return null;
	}

	@Override
	public float getXPos(){
		return this.position.getComponent(0);
	}

	@Override
	public float getYPos(){
		return this.position.getComponent(1);
	}
	
	@Override
	public IVector getPosition() {
		return this.position;
	}
}
