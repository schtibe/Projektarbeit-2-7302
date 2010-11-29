package gui;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import common.IVector;

import environment.SpeedWayPoint;

public class UIAdapterSpeedWayPoint extends UIAdapter<SpeedWayPoint> implements IUIAdapterSpeedWayPoint {
	
	private Rectangle shape;

	public UIAdapterSpeedWayPoint(SpeedWayPoint mainObject, float scale, IVector offsetVector) throws Exception {
		super(mainObject);
		this.shape = new Rectangle(
				0, 
				0, 
				10f, 
				10f
		);
		
		this.shape.setCenterX(this.mainObject.getXPos() * scale + offsetVector.getComponent(0));
		this.shape.setCenterY(this.mainObject.getYPos() * scale + offsetVector.getComponent(1)); 
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Shape getShape() {
		return this.shape;
	}
	
	/**
	 * Return the speed limit
	 */
	@Override
	public String toString() {
		return "" + this.mainObject.getSpeedLimit();
	}
}