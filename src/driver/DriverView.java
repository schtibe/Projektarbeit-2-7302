package driver;

import common.IVector;
import common.LinearCombination;
import common.Vector;

public class DriverView implements IDriverView {
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
	public DriverView(){
		
	}
	
	/**
	 * constructor with direction and position
	 * @param direction
	 * @param position
	 */
	public DriverView(IVector direction,IVector position){
		this.direction = direction;
		this.position = position;
	}
	
	/**
	 * constructor with angle and distance
	 * @param angle
	 * @param distance
	 */
	
	public DriverView(float angle, float distance){
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
	
	private DriverView(IVector direction, IVector position, float angle, float distance){
		this.direction = direction;
		this.position = position;
		this.angle = angle;
		this.distance = distance;
	}
	/**
	 * sets the angle 
	 */
	@Override
	public void setAngle (float value){
		this.angle = value;
		this.boundaryCalculated = false;
	}
	
	/**
	 * sets the distance
	 */
	
	@Override
	public void setDistance (float value){
		this.distance = value;
		this.boundaryCalculated = false;
	}
	
	/**
	 * sets the direction vector
	 */
	
	@Override
	public void setDirection (IVector value){
		this.direction = value;
		this.boundaryCalculated = false;
	}
	
	/**
	 * sets the position
	 */
	
	@Override
	public void setPosition (IVector value){
		this.position = value;
	}
	
	/**
	 * get the angle
	 */
	
	@Override
	public float getAngle (){
		return this.angle;
	}
	
	/**
	 * get the distance
	 */
	
	@Override
	public float getDistance (){
		return this.distance;
		
	}
	
	/**
	 * get the position
	 */
	
	@Override
	public IVector getPosition (){
		return this.position;
	}
	
	/**
	 * get the direction vector
	 */
	
	@Override
	public IVector getDirection (){
		return this.direction;
	}
	
	/**
	 * returns a clone of the actual DriverView
	 */
	
	@Override
	public IDriverView clone (){
		return new DriverView(this.direction,this.position,this.angle,this.distance);
	}
	
	/**
	 * to string
	 */
	@Override
	public String toString(){
		return new String("position: "+this.position.toString()+", direction: "+this.direction.toString()+", distance: "+this.distance);
	}

	/**
	 * Check whether the way point lies in this area
	 * @param position
	 * @return
	 */
	public boolean checkWayPoint(IVector position) {
	//	System.out.println("Calculating constants");
		IVector a = getABoundary();

		IVector b = getBBoundary();

		float boundaryRatio =  b.norm() /  a.norm();

		LinearCombination result = Vector.getLinearCombination(this.position, a, b, position);

		float ratio = result.getMu() * b.norm() / result.getLambda() * a.norm();

		return ratio >= boundaryRatio && result.getLambda() > 0 && result.getLambda() <=1 && result.getMu() > 0 && result.getMu() <= 1;
	}

	@Override
	public IVector getABoundary() {
		calculateBoundaries();
		return aBoundary;
	}

	@Override
	public IVector getBBoundary() {
		calculateBoundaries();
		return bBoundary;
	}

	@Override
	public IVector getCBoundary() {
		calculateBoundaries();
		return cBoundary;
	}
	
	/**
	 * calculates the boundaries of our view
	 * 
	 * @TODO The distance is wrong
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
	
}
