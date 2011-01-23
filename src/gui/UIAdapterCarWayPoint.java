package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import common.IVector;

import environment.CarWayPoint;

/**
 * The adapter for a car
 */
public class UIAdapterCarWayPoint extends UIAdapter<CarWayPoint> implements IUIAdapterCarWayPoint {

	private Rectangle shape;
	
	public UIAdapterCarWayPoint(
		CarWayPoint mainObject,
		float scale,
		IVector offsetVector
	) throws Exception {
		super(mainObject);
		
		this.shape = new Rectangle(0, 0, 10f, 10f);
		
		this.shape.setCenterX(
			this.mainObject.getXPos() *
			scale + offsetVector.getComponent(0)
		);
		
		this.shape.setCenterY(
				this.mainObject.getYPos() * 
				scale + offsetVector.getComponent(1)
		); 
	}
	
	@Override
	public Shape getShape() {
		return this.shape;
	}
	
	@Override
	public boolean doDraw() {
		return true;
	}

	@Override
	public void draw(Graphics g, TrueTypeFont ttf) {
		if (this.doDraw()) {
			g.setColor(Color.blue);
			g.draw(this.getShape());
		}
		
	}
}
