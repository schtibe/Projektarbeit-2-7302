package driver;

import java.util.List;

import common.GlobalConstants;

/**
 * The physics properties of a driver
 */
public class Physics {
	
	private float sight;
	private float fieldOfView;
	private int updateInterval;
	private List<Drug> drugs;
	
	/**
	 * constructor for physics object
	 * @param sight How far the driver can see
	 * @param fov The angle of the view
	 * @param interval The interval to assess the situation
	 * @param drugs Drugs that are taken
	 */
	public Physics (float sight, float fov, int interval, List<Drug> drugs){
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
	 * extends the driver view by its physics components
	 * @param view
	 * @return The adjusted view
	 */
	public IDriverView getView(IDriverView view){
		view.setAngle(this.fieldOfView); 
		view.setDistance(this.sight);
		return view;
	}
	
	/**
	 * returns the interval value
	 * @return Interval
	 */
	public int getUpdateInterval (){
		return this.updateInterval;
	}
}
