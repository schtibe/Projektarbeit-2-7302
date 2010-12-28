package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Shape;

/**
 * Since we dont want to draw this stuff, we don't implement the methods
 *
 */
public class UIAdapterCarWayPoint implements IUIAdapterCarWayPoint {

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawString(TrueTypeFont ttf) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean doDraw() {
		return false;
	}

}
