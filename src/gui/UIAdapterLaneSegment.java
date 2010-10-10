package gui;

import environment.ILaneSegment;

import java.util.ArrayList;
import java.util.List;

import common.IVector;

/**
 * A lane segment adapter implementation
 */
public class UIAdapterLaneSegment extends UIAdapter<ILaneSegment<?>> 
		implements IUIAdapterLaneSegment<ILaneSegment<?>>{
	
	private List<IVector> path ;
	private static int counter;
	
	public UIAdapterLaneSegment(ILaneSegment<?> mainObject) throws Exception {
		super(mainObject);
		this.path = new ArrayList<IVector>();
		this.generatePath();
		counter++;
	}
	
	/**
	 * Generates the path
	 */
	private void generatePath() {
		this.path.add(this.mainObject.getStartPoint());
		this.path.addAll(this.getBezierList());
		this.path.add(this.mainObject.getEndPoint());
	}
	
	/**
	 * Generates the Bezier curve
	 * @return the new bezier curve
	 */
	private List<IVector> getBezierList() {
		return this.getBezierListRecursive(this.mainObject.getStartPoint(),this.mainObject.getEndPoint(),0f,1f);
	}
	
	/**
	 * Generates recursively the Bezier curve 
	 * @return the new Bezier curve
	 */
	private List<IVector> getBezierListRecursive(IVector start, IVector end, float startFloat, float endFloat){
		List<IVector> vectors = new ArrayList<IVector>();
		float middleShizzle = (startFloat+endFloat)/2;
		
		IVector middleVec = this.mainObject.getPointOnCurve(middleShizzle);
	
		if ((middleVec.sub(start).norm()) <= 10f) {
			vectors.add(middleVec); 
		} else {
			vectors.addAll(this.getBezierListRecursive(start, middleVec,startFloat,middleShizzle));
			
			vectors.add(middleVec);
			
			vectors.addAll(this.getBezierListRecursive(middleVec,end, middleShizzle,endFloat));
		}
		return vectors;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<IVector> getPath() {
		return this.path;
	}
}
