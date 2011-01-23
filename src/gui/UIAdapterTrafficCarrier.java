package gui;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import common.IVector;
import common.Vector;

import environment.ILane;
import environment.ITrafficCarrier;

/**
 * A traffic carrier implementation
 */
public class UIAdapterTrafficCarrier extends UIAdapter<ITrafficCarrier> implements IUIAdapterTrafficCarrier<ITrafficCarrier>{
	protected Shape shape;
	protected List<IUIAdapterLane<?>> lanes = new ArrayList<IUIAdapterLane<?>>();
	protected IVector maxPos, minPos;
	protected float scale;
	
	public UIAdapterTrafficCarrier(ITrafficCarrier mainObject, float scale) throws Exception {
		super(mainObject);

		this.scale = scale;
		
		for(ILane lane : mainObject.getLeftLanes()) {
			lanes.add(UIElementFactory.getUIElement(lane, scale));
		}
		for(ILane lane : mainObject.getRightLanes()) {
			lanes.add(UIElementFactory.getUIElement(lane, scale));
		}
		
		this.generateMaxMinPos();
	}
	
	/**
	 * Calculates the x and y min and pax pos of this opject
	 */
	private void generateMaxMinPos() {
		if (this.lanes.size()>0){
			float maxPos[] = {0,0};
			float minPos[] = {this.lanes.get(0).getMinPos().getComponent(0),
							  this.lanes.get(0).getMinPos().getComponent(1)};
			
			for(IUIAdapterLane<?> lane : this.lanes){
				for(int ix=0; ix <= 1; ix++){
					//Max position
					if(maxPos[ix] <  lane.getMaxPos().getComponent(ix)){
						maxPos[ix] = lane.getMaxPos().getComponent(ix);
					}
					//Min position
					if(minPos[ix] > lane.getMinPos().getComponent(ix)){
						minPos[ix] = lane.getMinPos().getComponent(ix);
					}
				}
			}
			this.maxPos = new Vector(maxPos);
			this.minPos = new Vector(minPos);
		}else{
			this.maxPos = this.minPos = new Vector(new float[]{0,0});
		}
	}
	
	@Override
	public IVector getMaxPos() {
		return this.maxPos;
	}
	
	@Override
	public IVector getMinPos() {
		return this.minPos;
	}
	
	@Override
	public List<IUIAdapterLane<?>> getLanes() {
		return this.lanes;
	}

	@Override
	public void setScale(float scale) throws Exception {
		for(IUIAdapterLane<?> lane : this.lanes) {
			lane.setScale(scale);
		}
		//calculate the new max and min pos
		this.generateMaxMinPos();
	}

	@Override
	public void move(IVector pos) {	
		for(IUIAdapterLane<?> lane : this.lanes) {
			lane.move(pos);
		}
		//calculate the new max and min pos
		this.generateMaxMinPos();
	}
}
