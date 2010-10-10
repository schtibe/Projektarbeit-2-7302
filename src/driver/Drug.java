package driver;

public abstract class Drug {
	
	/**
	 * prepared class can be used later to influence character and physics
	 */
	
	protected int effectOnInterval;
	protected float effectOnSight;
	protected float effectOnFieldOfView;
	
	public int intervalEffects(){
		return effectOnInterval;
	}
	
	public float sightEffects(){
		return effectOnSight;
	}
	
	public float fieldOfViewEffects() {
		return effectOnFieldOfView;
	}	
}
