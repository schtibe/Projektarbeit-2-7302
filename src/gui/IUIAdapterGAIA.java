package gui;

import java.util.List;

import org.newdawn.slick.Graphics;

/**
 * The adapter interface for a gaia (world)
 * @param <E> the class to adapt
 */
public interface IUIAdapterGAIA<E>  extends IUIAdapter<E> {

	/**
	 * Get a list of all IUIAdapterTrafficCarrier
	 * @return a list of all IUIAdapterTrafficCarrier
	 */
	public List<IUIAdapterTrafficCarrier<?>> getRoads();
	
	/**
	 * Get a list of all IUIAdapterVehicle
	 * @return a list of all IUIAdapterVehicle
	 */
	public List<IUIAdapterVehicle<?>> getVehicles();
	
	/**
	 * Get the scale of this gaia
	 * @return the scale of this gaia
	 */
	public float getScale();
	
	/**
	 * Add a new vehicle to the GAIA if the lane allows it
	 * @param mouseY 
	 * @param mouseX 
	 */
	public void addVehicle(
			IUIAdapterLane<?> lane,
			int mouseX,
			int mouseY
	) throws Exception;
	
	/**
	 * Destroys the gaia
	 */
	public void destroy();
	
	/**
	 * Get all IUIAdapterWayPoint
	 * @return all IUIAdapterWayPoint
	 */
	public List<IUIAdapterWayPoint<?>> getWaypoints();
}