package gui;

public class GUIConstants {
	
	/**
	 * Singleton instance
	 */
	private static GUIConstants instance;
	
	public synchronized static GUIConstants getInstance() {
		if (GUIConstants.instance == null) {
			GUIConstants.instance = new GUIConstants();
		}
		
		return GUIConstants.instance;
	}
	
	/**
	 * Private constructor
	 */
	private GUIConstants() {}
	
	
	/**
	 * Whether the position of the junction way points are to be shown
	 */
	private boolean showJunctionWaypointPosition = false;
	public boolean showJunctionWaypointPosition() {
		return this.showJunctionWaypointPosition;
	}
	public boolean toggleShowJunctionWaypointPosition() {
		this.showJunctionWaypointPosition = !this.showJunctionWaypointPosition;
		return this.showJunctionWaypointPosition;
	}
	
	/**
	 * Wheter the position of the speed way points are to be shown
	 */
	private boolean showSpeedWaypointPosition = true;
	public boolean showSpeedWaypointPosition() {
		return this.showSpeedWaypointPosition;
	}
	public boolean toggleShowSpeedWaypointPosition() {
		this.showSpeedWaypointPosition = !this.showSpeedWaypointPosition;
		return this.showSpeedWaypointPosition;
	}
}
