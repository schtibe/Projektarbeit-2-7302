package driver;

import common.GlobalConstants;



public class Character {
	
	protected double riskyness = 0.5d;
	protected double temperament = 0.5d;

	/**
	 * 
	 * @param val between 0 an 1 where 0.5 is the neutral (default) value;
	 * 0: takes no risk at all
	 * 1: takes maximum possible risk
	 */
	
	public void setRiskyness(double val){
		this.riskyness = val%1.0d;
	}
	
	/**
	 * 
	 * @return the riskyness value
	 */
	
	public double getRiskyness(){
		return this.riskyness;
	}
	
	/**
	 * 
	 * @param val between 0 an 1 where 0.5 is the neutral (default) value;
	 * 0: consider the guy phlegmatic
	 * 1: someone is a real volcano
	 */
	
	public void setTemperament (double val){
		this.temperament = val%1.0d;
	}
	
	/**
	 * 
	 * @return the temperament
	 */
	
	public double getTemperament(){
		return this.temperament;
	}
	
	public DecelerationActivator changeActivator (DecelerationActivator dec){
		float percentage = 1.0f;
		if (temperament < 0.5){
			percentage += GlobalConstants.getInstance().getTemperamentInfluence() * (1f-(float)2*temperament);
		}
		if (riskyness < 0.5){
			percentage += GlobalConstants.getInstance().getRiskynessInfluence() * (1f-(float)2*riskyness);
		}
		float newVal = dec.getValue()*percentage;
		dec.setValue((newVal > 1.0f)?1.0f:newVal);
		return dec;
	}
	
	public AccelerationActivator changeActivator (AccelerationActivator acc){
		float percentage = 1.0f;
		if (temperament > 0.5){
			percentage += GlobalConstants.getInstance().getTemperamentInfluence() * ((float)2*temperament-1f);
		}
		if (riskyness > 0.5){
			percentage += GlobalConstants.getInstance().getRiskynessInfluence() * ((float)2*riskyness-1f);
		}
		float newVal = acc.getValue()*percentage;
		acc.setValue((newVal > 1.0f)?1.0f:newVal);
		return acc;
	}
}
