package gui;

import org.newdawn.slick.Color;
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
	@Override
	public Shape getShape() {
		return this.shape;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.mainObject.toString();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColor() {
		return new Color(Color.white);
	}
	

}
