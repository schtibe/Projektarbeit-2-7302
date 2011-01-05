package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public interface IUIAdapterDrawable {
	/**
	 * Draw the thing
	 * 
	 * @TODO Probably also put the ttf there
	 */
	public void draw(Graphics g, TrueTypeFont ttf);
}
