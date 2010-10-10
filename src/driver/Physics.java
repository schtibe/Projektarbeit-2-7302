package driver;

import java.util.List;

import common.GlobalConstants;

//import common.IVector;

public class Physics {
	
	private float sight;
	private float fieldOfView;
	private int updateInterval;
	private List<Drug> drugs;
	
	/**
	 * constructor for physics object
	 * @param sight
	 * @param fov
	 * @param interval
	 * @param drugs
	 */
	
	public Physics (float sight,float fov,int interval,List<Drug> drugs){
		this.sight = sight * GlobalConstants.getInstance().getScale();
		this.fieldOfView = fov;
		this.updateInterval = interval;
		this.drugs = drugs;
		for (Drug drug : this.drugs){
			this.sight += drug.sightEffects();
			this.fieldOfView += drug.fieldOfViewEffects();
			this.updateInterval += drug.intervalEffects();
		}
	}
	
	/**
	 * extends the driver view by it's physics components
	 * @param view
	 * @return
	 */
	
	public IDriverView getView(IDriverView view){
		IDriverView output = view.clone();
		output.setAngle(this.fieldOfView); 
		output.setDistance(this.sight);
		return output;
	}
	
	/**
	 * returns the interval value
	 * @return
	 */
	
	public int getUpdateInterval (){
		return this.updateInterval;
	}
}
