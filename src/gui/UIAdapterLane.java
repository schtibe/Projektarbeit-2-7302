package gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Ellipse;
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
	 */
	private void generatePath() {
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

	@Override
	public float getPositionOnLane(int mouseX, int mouseY) {
		List<Path> paths = this.getLaneSegmentPaths();
		
		IUIAdapterLaneSegment<?> laneSeg = null;
		int pos = 0;
		for (Path p: paths) {
			if (p.intersects(new Ellipse(mouseX, mouseY, 2, 2))) {
				laneSeg = laneSegments.get(pos);
				break;
			}
			
			pos ++;
		}
		
		if (laneSeg == null) {
			return 0;
		}
		
		try {
			return ((ILaneSegment<?>) laneSeg.getMainObject()).positionIntersection(
				new Vector(
					new float[]{mouseX / this.scale, mouseY / this.scale}
				)
			);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Return paths objects of the lane segments
	 * @return
	 */
	@Override
	public List<Path> getLaneSegmentPaths() {
		List<Path> paths = new ArrayList<Path>();
		
		for(IUIAdapterLaneSegment<?> laneSegment : laneSegments) {
			List<IVector> points = laneSegment.getPath();
			if (points.size() > 0) {
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
		}
		
		return paths;
	}
			
	@Override
	public IVector getMinPos() {
		return new Vector(new float[]
				              {this.getPath().getMinX(),
							   this.getPath().getMinY()});
	}
	

	@Override
	public IVector getMaxPos() {
		return new Vector(new float[]
		                      {this.getPath().getMaxX(),
							   this.getPath().getMaxY()});
	}

	@Override
	public Path getPath() {
		if (this.gPath == null) {
			this.generatePath();
		}
		return this.gPath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setScale(float scale) throws Exception {
		this.scale = scale;
		this.gPath = null; // reset the path
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
	
	@Override
	public boolean vehiclePlacable() {
		return this.mainObject.vehiclePlacable();
	}

	
	@Override
	public void draw(Graphics g, TrueTypeFont ttf) {
		g.setColor(GlobalConstants.getInstance().getPathColor());
		g.draw(this.getPath());
	}

}
