package environment;

import gui.IUIAdapterWayPoint;
import gui.UIElementFactory;

import common.IVector;

import driver.Animus;

public class JunctionWayPoint extends StaticWayPoint {

	/**
	 * instance variables
	 */
	protected IJunction junction;
	
	/**
	 * constructor
	 * @param lane
	 * @param junction
	 */
	public JunctionWayPoint (ILane lane, IJunction junction) {
		super(lane, null);
		this.junction = junction;
		
		this.setPos();
	}
	
	/**
	 * return the corresponding junction
	 * @return IJunction
	 */
	public IJunction getJunction(){
		return this.junction;
	}
	
	/*
	* the next two methods are inconsistent as far as they call a method getVehiclePosition() when they actually
	* are on a WayPoint. Nevertheless the method is the correct one as it gets the coordinates according to a percentage
	* on the lane.
	* the value of 0.8 indicates that you see the junction as early as you have passed 79% percent of the lanes length
	* before the junction.
	*/
	protected void setPos() {
		try {
			this.position = this.lane.getPositionOnLane((this.lane.getLength()*0.8f));
		} catch (LaneLengthExceededException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void visitHandleWayPoint(Animus animus) {
		animus.handleWayPoint(this);
	}

	/**
	 * {@inheritDoc}
	 * @throws Exception 
	 */
	@Override
	public IUIAdapterWayPoint<?> visitUIFactory(
			float scale,
			IVector offsetVector
	) throws Exception {
		return UIElementFactory.getUIElement(this, scale, offsetVector);
	}

	@Override
	public String toString(){
		return "type: junction\ncoordinates: "+this.position.toString();
	}

}
