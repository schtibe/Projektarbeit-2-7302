package gui;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import common.IVector;

import environment.IWayPoint;

/**
 * A way point adapter implementation
 */
public class UIAdapterJunctionWayPoint extends UIAdapter<IWayPoint> implements IUIAdapterJunctionWayPoint {
	/**
	 * A shape which represents the way point
	 */
	protected Shape shape;
	
	public UIAdapterJunctionWayPoint(
			IWayPoint mainObject, 
			float scale, 
			IVector offsetVector
	) throws Exception {
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
	public Shape getShape() {
		return this.shape;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return this.mainObject.toString();
	}
	

}
