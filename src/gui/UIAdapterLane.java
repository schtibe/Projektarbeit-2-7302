package gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Path;

import common.GlobalConstants;
import common.IVector;
import common.Vector;

import environment.ILane;
import environment.ILaneSegment;


/**
 * An lane adapter implementation
 */
public class UIAdapterLane extends UIAdapter<ILane> implements IUIAdapterLane<ILane> {

	protected IVector maxPos, minPos, startPos;
	protected Path gPath;
	protected float scale = 1;
	protected List<IUIAdapterLaneSegment<?>> laneSegments = new ArrayList<IUIAdapterLaneSegment<?>>();
	
	public UIAdapterLane(ILane mainObject, float scale) throws Exception {
		super(mainObject);
		this.generateLaneSegments();
		this.generatePath();
	}
	
	/**
	 * Generates all lane segments by using the factory
	 * @throws Exception
	 */
	private void generateLaneSegments() throws Exception {
		//create all lane segment adapters for this lane
		ILaneSegment<?> currentLaneSegment= this.mainObject.getFirstILaneSegment();
		
		while (currentLaneSegment != null) {
			this.laneSegments.add(UIElementFactory.getUIElement(currentLaneSegment));				
			currentLaneSegment = (ILaneSegment<?>) currentLaneSegment.getNextLaneSegment();
		}
	}
	
	/**
	 * Generates the scaled path for this lane
	 * @throws Exception
	 */
	private void generatePath() throws Exception {
		//create the empty lane path 
		this.gPath = new Path(
				this.mainObject.getStartPosition().getComponent(0)* this.scale,
				this.mainObject.getStartPosition().getComponent(1)* this.scale);
				
		//Join all lane segments
		for(IUIAdapterLaneSegment<?> laneSegment : laneSegments) {
			for(IVector pos : laneSegment.getPath()) {
				gPath.lineTo(pos.getComponent(0) * this.scale, 
							 pos.getComponent(1) * this.scale);
			}
		}
	}
	
	public List<Path> getLaneSegmentPaths() {
		List<Path> paths = new ArrayList<Path>();
		
		for(IUIAdapterLaneSegment<?> laneSegment : laneSegments) {
			List<IVector> points = laneSegment.getPath();
			IVector startPos = points.remove(0);
			Path p = new Path(
					startPos.getComponent(0) * this.scale, 
					startPos.getComponent(1) * this.scale
			);
			for (IVector point: points) {
				p.lineTo(
						point.getComponent(0) * this.scale,
						point.getComponent(1) * this.scale
				);
			}

			paths.add(p);
		}
		
		return paths;
	}
			
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector getMinPos() {
		return new Vector(new float[]
				              {this.gPath.getMinX(),
							   this.gPath.getMinY()});
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector getMaxPos() {
		return new Vector(new float[]
		                      {this.gPath.getMaxX(),
							   this.gPath.getMaxY()});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getPath() {
		return this.gPath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setScale(float scale) throws Exception {
		this.scale = scale;
		//calculate the new path
		this.generatePath();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void move(IVector pos) {
		this.gPath.setX(pos.getComponent(0));
		this.gPath.setY(pos.getComponent(1));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ILane getOriginalLane() {
		return this.mainObject;
	}
	
	/**
	 * Returns the color
	 */
	@Override
	public Color getColor() {
		return GlobalConstants.getInstance().getPathColor();
	}

	
	@Override
	public boolean vehiclePlacable() {
		return this.mainObject.vehiclePlacable();
	}
}
