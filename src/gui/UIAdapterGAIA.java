package gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Path;

import car.IVehicle;
import car.VehicleFactory.VehicleType;

import common.GlobalConstants;
import common.IVector;
import common.Vector;

import driver.Character;
import driver.Drug;
import driver.Physics;
import environment.IGaia;
import environment.ILane;
import environment.ILaneSegment;
import environment.ITrafficCarrier;
import environment.IWayPoint;
import environment.WayPointManager;

/**
 * A GAIA adapter implementation 
 */
public class UIAdapterGAIA extends UIAdapter<IGaia>
	implements IUIAdapterGAIA<IGaia> {

	protected List<IUIAdapterTrafficCarrier<?>> trafficCarriers = new ArrayList<IUIAdapterTrafficCarrier<?>>();
	protected List<IUIAdapterVehicle<?>> vehicles = new ArrayList<IUIAdapterVehicle<?>>();
	protected List<IUIAdapterWayPoint<?>> wayPoints = new ArrayList<IUIAdapterWayPoint<?>>();
	
	protected float scale, xMax, yMax;
	protected IVector maxPos, minPos, correctionVector;
	
	public UIAdapterGAIA(IGaia mainObject, float xMax, float yMax) throws Exception {
		super(mainObject);
		
		/*
		 * Generates the map
		 */
		if(this.mainObject.getTrafficCarriers().isEmpty()) {
			throw new Exception("No roads are available.");
		}
		
		//Screen resolution
		this.xMax = xMax - GlobalConstants.getInstance().getBorder().getComponent(0) * 2;
		this.yMax = yMax - GlobalConstants.getInstance().getBorder().getComponent(1) * 2;

				
		for(ITrafficCarrier trafficCarrier : this.mainObject.getTrafficCarriers()) {
			this.trafficCarriers.add(UIElementFactory.getUIElement(trafficCarrier, this.scale));
		}
		
		//Get the max and min position of all roads
		this.maxPos = this.getMaxPos();
		this.minPos = this.getMinPos();
		
		this.scale = this.calculateScale();
		
		//scale all and move roads in upper right corner
		this.correctionVector = minPos.multiply(-1).multiply(scale).add(new Vector(
				new float[]{
						GlobalConstants.getInstance().getBorder().getComponent(0),
						GlobalConstants.getInstance().getBorder().getComponent(1)}));
		
		for(IUIAdapterTrafficCarrier<?> road : trafficCarriers) {
			road.setScale(this.scale);
			road.move(correctionVector);
		}
		
		//Get the max and min position of all roads
		this.maxPos = this.getMaxPos();
		this.minPos = this.getMinPos();
		
		/*
		 * TODO refactor this into a method
		 */
		for(IVehicle vehicle : this.mainObject.getVehicles()) {
			this.vehicles.add(UIElementFactory.getUIElement(
					vehicle, 
					this.scale, 
					correctionVector
				)
			);
		}
		
		/*
		 * TODO refactor this into a method
		 */
		for(IWayPoint wayPoint : this.mainObject.getWayPoints()) {
			this.wayPoints.add(UIElementFactory.getUIElement(
					wayPoint, 
					this.scale, 
					correctionVector
			));
		}
	}

	/**
	 * Fits the whole map to one screen
	 * @return the scale factor to fit the map on one screen.
	 */
	private float calculateScale() {
		float xScale, yScale;
		
		//Try to fit into the screen
		xScale = xMax / (this.maxPos.getComponent(0) - this.minPos.getComponent(0));
		yScale = yMax / (this.maxPos.getComponent(1) - this.minPos.getComponent(1));
		
		if(xScale < yScale) {
			return xScale;
		}
		return yScale;
	}
	
	/**
	 * Evaluates the max. positions (Vectors) of all lanes.
	 * @return the max. position
	 */
	private IVector getMaxPos() {
		float[] currentMaxPos = {0,0};
		
		for(IUIAdapterTrafficCarrier<?> road : this.trafficCarriers){
			for(int ix = 0; ix <= 1; ix++){
				if(currentMaxPos[ix] < road.getMaxPos().getComponent(ix)){
					currentMaxPos[ix] = road.getMaxPos().getComponent(ix);
				}
			}
		}
		
		return new Vector(currentMaxPos);
	}
	
	/**
	 * Evaluates the min. positions (Vectors) of all lanes.
	 * @return the min. position
	 */
	private IVector getMinPos() {
		float[] currentMinPos = {this.trafficCarriers.get(0).getMinPos().getComponent(0),
								 this.trafficCarriers.get(0).getMinPos().getComponent(0)};
		
		for(IUIAdapterTrafficCarrier<?> road : this.trafficCarriers){
			for(int ix = 0; ix <= 1; ix++){
				if(currentMinPos[ix] > road.getMinPos().getComponent(ix)){
					currentMinPos[ix] = road.getMinPos().getComponent(ix);
				}
			}
		}
		
		return new Vector(currentMinPos);
	}
	
	@Override
	public List<IUIAdapterTrafficCarrier<?>> getRoads() {
		return this.trafficCarriers;
	}

	/**
	 * Get a list of all IUIAdapterVehicle
	 * @return a list of all IUIAdapterVehicle
	 */
	@Override
	public List<IUIAdapterVehicle<?>> getVehicles() {
		return this.vehicles;
	}

	@Override
	public float getScale() {
		return this.scale;
	}

	@Override
	public void addVehicle(IUIAdapterLane<?> lane, int mouseX, int mouseY) throws Exception {
		if (!lane.vehiclePlacable()) {
			return;
		}
		
		/**
		List<Path> paths = lane.getLaneSegmentPaths();
		
		ILaneSegment<?> laneSeg;
		for (Path p: paths) {
			if (p.intersects(new Ellipse(mouseX, mouseY, 2, 2)) {
				
			}
		}
		*/
		
		this.vehicles.add(
			UIElementFactory.getUIElement(
				this.mainObject.addRoadUser(
					VehicleType.car, 
					lane.getOriginalLane(),  
					new Character(), 
					new Physics(
						200,
						(float)((Math.PI)/3),
						250, 
						new ArrayList<Drug>()
					)
				),
				this.scale,
				this.correctionVector
			)
		);
	}

	@Override
	public void destroy() {
		this.mainObject.destroy();
	}

	@Override
	public List<IUIAdapterWayPoint<?>> getWaypoints() {
		return this.wayPoints;
	}

	public void removeVehicleAdapter() {
		
	}
}
