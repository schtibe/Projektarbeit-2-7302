package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public interface IUIAdapterDrawable {
	/**
	 * Draw the object
	 * 
	 * @param g The graphics utility
	 * @param ttf The font utility
	 */
	public void draw(Graphics g, TrueTypeFont ttf);
}
