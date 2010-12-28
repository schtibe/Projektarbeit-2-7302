package gui;

import car.IVehicle;

import common.IVector;

import environment.CarWayPoint;
import environment.IGaia;
import environment.ILane;
import environment.ILaneSegment;
import environment.ITrafficCarrier;
import environment.IWayPoint;
import environment.JunctionWayPoint;
import environment.SpeedWayPoint;


/**
 * Factory for all UIElement adapters
 */
public class UIElementFactory {

	/**
	 * generates a new IUIAdapterTrafficCarrier 
	 * @param road
	 * @param scale
	 * @return a new IUIAdapterTrafficCarrier
	 * @throws Exception
	 */
	public static IUIAdapterTrafficCarrier<?> getUIElement(ITrafficCarrier road, float scale) throws Exception{
		return new UIAdapterTrafficCarrier(road, scale);
	}
	
	/**
	 * generates a new IUIAdapterLane
	 * @param lane
	 * @param scale
	 * @return a new IUIAdapterLane
	 * @throws Exception
	 */
	public static IUIAdapterLane<?> getUIElement(ILane lane, float scale) throws Exception{
		return new UIAdapterLane(lane, scale);
	}
	
	/**
	 * Generates a new IUIAdapterGAIA
	 * b
	 * @param GAIA
	 * @param xMax
	 * @param yMax
	 * @return a new IUIAdapterGAIA
	 * @throws Exception
	 */
	public static IUIAdapterGAIA<?> getUIElement(IGaia GAIA, float xMax, float yMax) throws Exception{
		return new UIAdapterGAIA(GAIA, xMax, yMax);
	}
	
	/**
	 * Generates a new IUIAdapterVehicle
	 * 
	 * @param vehicle
	 * @param scale
	 * @param offsetVector
	 * @return a new IUIAdapterVehicle
	 * @throws Exception
	 */
	public static IUIAdapterVehicle<?> getUIElement(IVehicle vehicle, float scale, IVector offsetVector) throws Exception {
		return new UIAdapterVehicle(vehicle, scale, offsetVector);
	}
	
	/**
	 * Generates a new IUIAdapterLaneSegment
	 * 
	 * @param laneSegment
	 * @return a new IUIAdapterLaneSegment
	 * @throws Exception
	 */
	public static IUIAdapterLaneSegment<?> getUIElement(ILaneSegment<?> laneSegment) throws Exception {
		return new UIAdapterLaneSegment(laneSegment);
	}
	
	/**
	 * Generates a new IUIAdapterWayPoint
	 * 
	 * @param wayPoint
	 * @param scale
	 * @param offsetVector
	 * @return a new IUIAdapterWayPoint
	 * @throws Exception
	 */
	public static IUIAdapterWayPoint<?> getUIElement(
			IWayPoint wayPoint, 
			float scale, 
			IVector offsetVector
	) throws Exception {
		return wayPoint.visitUIFactory(scale, offsetVector); 
	}
	
	/**
	 * Create an adapter for 
	 * 
	 * @param wayPoint
	 * @param scale
	 * @param offsetVector
	 * @return
	 * @throws Exception 
	 */
	public static IUIAdapterSpeedWayPoint getUIElement(
			SpeedWayPoint wayPoint,
			float scale,
			IVector offsetVector
	) throws Exception {
		return new UIAdapterSpeedWayPoint(wayPoint, scale, offsetVector);
	}
	
	/**
	 * 
	 * @param wayPoint
	 * @param scale
	 * @param offsetVector
	 * @return
	 * @throws Exception 
	 */
	public static IUIAdapterJunctionWayPoint getUIElement(
			JunctionWayPoint wayPoint,
			float scale,
			IVector offsetVector
	) throws Exception {
		return new UIAdapterJunctionWayPoint(wayPoint, scale, offsetVector);
	}

	/**
	 * {@inheritDoc}
	 * @param wayPoint
	 * @param scale
	 * @param offsetVector
	 * @return
	 * @throws Exception
	 */
	public static IUIAdapterCarWayPoint getUIElement(
			CarWayPoint wayPoint,
			float scale,
			IVector offsetVector
	) throws Exception {
		return new UIAdapterCarWayPoint(wayPoint, scale, offsetVector);
	}
}
