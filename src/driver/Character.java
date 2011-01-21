package driver;

import common.GlobalConstants;

/**
 * The character of a driver
 */
public class Character {
	
	protected double riskyness = 0.5d;
	protected double temperament = 0.5d;

	/**
	 * Set the willingness to risk things
	 * 
	 * 0: takes no risk at all
	 * 1: takes maximum possible risk
	 * @param val between 0 an 1 where 0.5 is the neutral (default) value;
	 */
	public void setRiskyness(double val){
		this.riskyness = val%1.0d;
	}
	
	/**
	 * Return the willingness to risk things
	 * @return the riskyness value
	 */
	public double getRiskyness(){
		return this.riskyness;
	}
	
	/**
	 * Set the temperament of the driver
	 * 
	 * 0: consider the guy phlegmatic
	 * 1: someone is a real volcano
	 * @param val between 0 an 1 where 0.5 is the neutral (default) value;
	 */
	public void setTemperament (double val){
		this.temperament = val%1.0d;
	}
	
	/**
	 * Return the driver's temperament
	 * @return the temperament
	 */
	public double getTemperament(){
		return this.temperament;
	}
	
	/**
	 * Get a deceleration activator that considers the given values
	 * @param dec Deceleration activator
	 */
	public DecelerationActivator changeActivator (DecelerationActivator dec){
		float percentage = 1.0f;
		if (temperament < 0.5){
			percentage += GlobalConstants.getInstance().getTemperamentInfluence() * (1f-2*temperament);
		}
		if (riskyness < 0.5){
			percentage += GlobalConstants.getInstance().getRiskynessInfluence() * (1f-2*riskyness);
		}
		float newVal = dec.getValue()*percentage;
		dec.setValue((newVal > 1.0f)?1.0f:newVal);
		return dec;
	}
	
	/**
	 * Get a acceleration activator that considers the given values
	 * @param acc Acceleration activator
	 */
	public AccelerationActivator changeActivator (AccelerationActivator acc){
		float percentage = 1.0f;
		if (temperament > 0.5){
			percentage += GlobalConstants.getInstance().getTemperamentInfluence() * (2*temperament-1f);
		}
		if (riskyness > 0.5){
			percentage += GlobalConstants.getInstance().getRiskynessInfluence() * (2*riskyness-1f);
		}
		float newVal = acc.getValue()*percentage;
		acc.setValue((newVal > 1.0f)?1.0f:newVal);
		return acc;
	}
}
