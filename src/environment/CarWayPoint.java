package environment;

import gui.IUIAdapterWayPoint;
import gui.UIElementFactory;
import car.Car;

import common.IVector;

import driver.Animus;

public class CarWayPoint extends VehicleWayPoint {

	/**
	 * Construct with a car
	 * @param car
	 */
	public CarWayPoint(Car car) {
		super(car);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visitHandleWayPoint(Animus animus) {
		animus.handleWayPoint(this);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws Exception 
	 */
	public IUIAdapterWayPoint<?> 
		visitUIFactory(float scale, IVector offsetVector) throws Exception {
		return UIElementFactory.getUIElement(this, scale, offsetVector);
	}
}
