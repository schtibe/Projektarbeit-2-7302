package driver;

import common.IVector;

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
		return new DriverView(this.direction,this.position,this.angle,this.distance);
	}
	
	@Override
	public String toString(){
		return new String("position: "+this.position.toString()+", direction: "+this.direction.toString()+", distance: "+this.distance);
	}

	public boolean checkWayPoint(IVector position) {
		IVector a = this.getABoundary();
		IVector b = this.getBBoundary();

		position = position.sub(this.position);
		
		float ax = a.getComponent(0);
		float ay = a.getComponent(1);
		
		float bx = b.getComponent(0);
		float by = b.getComponent(1);
		
		float posx = position.getComponent(0);
		float posy = position.getComponent(1);
		
		float boundaryRatio =  b.norm() /  b.norm();
		float denominator = ax * by	- ay * bx;
		
		float lambda = -(bx * posy - by * posx) /denominator;
		
		float mu = (ax * posy - ay * posx) / denominator;
		
		float ratio = mu * b.norm() / lambda * b.norm();
			
		return ratio >= boundaryRatio && 
			lambda > 0 && lambda <=1 && 
			mu > 0 && mu <= 1;
	}

	
	@Override
	public IVector getABoundary() {
		this.calculateBoundaries();
		return this.aBoundary;
	}

	
	@Override
	public IVector getBBoundary() {
		this.calculateBoundaries();
		return this.bBoundary;
	}

	
	@Override
	public IVector getCBoundary() {
		this.calculateBoundaries();
		return this.cBoundary;
	}

	/**
	 * Calculate the boundaries of the view if necessary
	 * 
	 * The boundaries are relative to the position of the view.
	 */
	protected void calculateBoundaries() {
		if (boundaryCalculated) {
			return;
		}
		
		float halfAngle = angle / 2;
		
		this.aBoundary = (direction.rotate(halfAngle))
				.normalize().multiply(distance);
		this.cBoundary = (direction.rotate(-halfAngle))
				.normalize().multiply(distance);
		this.bBoundary = cBoundary.sub(this.aBoundary);
		
		this.boundaryCalculated = true;
	}
}
