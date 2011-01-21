package driver;

/**
 * Simulate a drug intake 
 */
public abstract class Drug {
	
	/**
	 * prepared class can be used later to influence character and physics
	 */	
	protected int effectOnInterval;
	protected float effectOnSight;
	protected float effectOnFieldOfView;
	
	/**
	 * The effect on the reaction time
	 * @return Interval effect
	 */
	public int intervalEffects(){
		return effectOnInterval;
	}
	
	/**
	 * The effect on the sight
	 * @return Sight effect
	 */
	public float sightEffects(){
		return effectOnSight;
	}
	
	/**
	 * The effect on angle of the view
	 * @return View angle effect
	 */
	public float fieldOfViewEffects() {
		return effectOnFieldOfView;
	}	
}
