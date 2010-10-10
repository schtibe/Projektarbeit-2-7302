package gui;

import java.awt.Image;
/*
 * Holds information which are relevant for the gui
 */
public class GameCache {
	private static GameCache instance;
	private IUIAdapterGAIA<?> GAIA;
	
	public synchronized static GameCache getInstance() {
        if (instance == null) {
            instance = new GameCache();
        }
        return instance;
    }
	/*
	 * stores one gaia
	 */
	public void setGAIA(IUIAdapterGAIA<?> GAIA) {
		this.GAIA = GAIA;
	}
	
	/*
	 * Get the gaia adapter
	 */
	public IUIAdapterGAIA<?> getGAIA() {
		return this.GAIA;
	}
}
