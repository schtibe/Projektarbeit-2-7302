package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
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

	@Override
	public Shape getShape() {
		return this.shape;
	}
	
	@Override
	public String toString() {
		return this.mainObject.toString();
	}
	
	/**
	 * Draw the string 
	 * @param ttf The font tool
	 */
	private void drawString(TrueTypeFont ttf) {
		if (GUIConstants.getInstance().showJunctionWaypointPosition()) {
			ttf.drawString(this.getShape().getCenterX(),
					   this.getShape().getCenterY(), 
					   this.toString(),Color.orange);
		}
	}
	
	@Override
	public boolean doDraw() {
		return true;
	}

	@Override
	public void draw(Graphics g, TrueTypeFont ttf) {
		if (this.doDraw()) {
			g.setColor(Color.white);
			g.draw(this.getShape());
			this.drawString(ttf);
		}
	
	}
	

}
