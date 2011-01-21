package driver;

import common.IRectangle;
import common.IVector;
import common.LinearCombination;
import common.Rectangle;
import common.Vector;

/**
 * The driver view shaped as a triangle
 */
public class DriverViewTriangle implements IDriverView {
	/**
	 * The properties of the view
	 */
	private IVector direction;
	private IVector position;
	private float angle;
	private float distance;
	
	/**
	 * The boundaries of the view
	 */
	IVector aBoundary, bBoundary, cBoundary;
	boolean boundaryCalculated = false;
	
	/**
	 * constructor, a driver view can be empty on construction time
	 */
	public DriverViewTriangle(){
		
	}
	
	/**
	 * constructor with direction and position
	 * @param direction
	 * @param position
	 */
	public DriverViewTriangle(IVector direction,IVector position){
		this.direction = direction;
		this.position = position;
	}
	
	/**
	 * constructor with angle and distance
	 * @param angle
	 * @param distance
	 */
	
	public DriverViewTriangle(float angle, float distance){
		this.angle = angle;
		this.distance = distance;
	}
	
	/**
	 * private constructor for complete instance creation
	 * @param direction
	 * @param position
	 * @param angle
	 * @param distance
	 */
	
	private DriverViewTriangle(IVector direction, IVector position, float angle, float distance){
		this.direction = direction;
		this.position = position;
		this.angle = angle;
		this.distance = distance;
	}

	@Override
	public void setAngle (float value){
		this.angle = value;
		this.boundaryCalculated = false;
	}

	@Override
	public void setDistance (float value){
		this.distance = value;
		this.boundaryCalculated = false;
	}
	
	
	@Override
	public void setDirection (IVector value){
		this.direction = value;
		this.boundaryCalculated = false;
	}

	@Override
	public void setPosition (IVector value){
		this.position = value;
	}
	

	@Override
	public float getAngle (){
		return this.angle;
	}
	
	@Override
	public float getDistance (){
		return this.distance;
		
	}
	
	@Override
	public IVector getPosition (){
		return this.position;
	}
	
	@Override
	public IVector getDirection (){
		return this.direction;
	}
	
	@Override
	public IDriverView clone (){
		return new DriverViewTriangle(this.direction,this.position,this.angle,this.distance);
	}
	
	@Override
	public String toString(){
		return new String("position: "+this.position.toString()+", direction: "+this.direction.toString()+", distance: "+this.distance);
	}


	@Override
	public boolean checkWayPoint(IVector position) {
		IVector a = getABoundary();
		IVector b = getBBoundary();
		float boundaryRatio =  b.norm() /  a.norm();

		LinearCombination result = Vector.getLinearCombination(this.position, a, b, position);

		float ratio = result.getMu() * b.norm() / result.getLambda() * a.norm();

		return ratio >= boundaryRatio && result.getLambda() > 0 && result.getLambda() <=1 && result.getMu() > 0 && result.getMu() <= 1;
	}

	private IVector getABoundary() {
		calculateBoundaries();
		return aBoundary;
	}

	private IVector getBBoundary() {
		calculateBoundaries();
		return bBoundary;
	}

	private IVector getCBoundary() {
		calculateBoundaries();
		return cBoundary;
	}
	
	/**
	 * calculates the boundaries of our view
	 */
	private void calculateBoundaries() {
		if(boundaryCalculated){
			return ;
		}
		aBoundary = this.direction.rotate(this.angle / 2)
		.normalize().multiply(this.distance);
		
		cBoundary = this.direction.rotate(- this.angle / 2)
		.normalize().multiply(this.distance);
		
		bBoundary = cBoundary.sub(aBoundary);
	}

	@Override
	public IRectangle getBoundingBox() {		
		IVector[] points = new IVector[2];
		
		points[0] = this.position.add(getCBoundary());
		points[1] = this.position.add(getABoundary());
		
		
		float maxX = this.position.getComponent(0);
		float maxY = this.position.getComponent(1);
		float minX = this.position.getComponent(0);
		float minY = this.position.getComponent(1);
		
		for (int i = 0;i<points.length;i++){
			float xComp = points[i].getComponent(0);
			if (xComp > maxX){
				maxX = xComp;
			}
			if (xComp < minX){
				minX = xComp;
			}
			float yComp = points[i].getComponent(1);
			if (yComp > maxY){
				maxY = yComp;
			}
			if (yComp < minY){
				minY = yComp;
			}
		}
		return new Rectangle (new Vector(new float[]{minX,minY}), new Vector(new float[]{maxX,maxY}));
	}
	
}
