package environment;

import gui.IUIAdapterWayPoint;
import gui.UIElementFactory;
import car.Car;

import common.IVector;

import driver.Animus;

public class CarWayPoint extends VehicleWayPoint {

	/**
	 * Construct a CarWayPoint
	 * @param car
	 */
	public CarWayPoint(Car car) {
		super(car);
	}

	@Override
	public void visitHandleWayPoint(Animus animus) {
		animus.handleWayPoint(this);
	}
	
	@Override
	public IUIAdapterWayPoint<?> 
		visitUIFactory(float scale, IVector offsetVector) throws Exception {
		return UIElementFactory.getUIElement(this, scale, offsetVector);
	}
}
