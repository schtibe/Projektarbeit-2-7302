package gui;

import environment.IWayPoint;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import common.IVector;

/**
 * A way point adapter implementation
 */
public class UIAdapterWayPoint extends UIAdapter<IWayPoint> implements IUIAdapterWayPoint<IWayPoint> {
	/**
	 * A shape which represents the waypoint
	 */
	protected Shape shape;
	
	public UIAdapterWayPoint(IWayPoint mainObject, float scale, IVector offsetVector) throws Exception {
		super(mainObject);
		this.shape = new Rectangle(0, 
								   0, 
								   10f, 
								   10f);
		
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
