package driver;

import common.IVector;

public class DriverView implements IDriverView {
	private IVector direction;
	private IVector position;
	private float angle;
	private float distance;
	
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
	public void setAngle (float value){
		this.angle = value;
	}
	
	/**
	 * sets the distance
	 */
	
	public void setDistance (float value){
		this.distance = value;
	}
	
	/**
	 * sets the direction vector
	 */
	
	public void setDirection (IVector value){
		this.direction = value;
	}
	
	/**
	 * sets the position
	 */
	
	public void setPosition (IVector value){
		this.position = value;
	}
	
	/**
	 * get the angle
	 */
	
	public float getAngle (){
		return this.angle;
	}
	
	/**
	 * get the distance
	 */
	
	public float getDistance (){
		return this.distance;
		
	}
	
	/**
	 * get the position
	 */
	
	public IVector getPosition (){
		return this.position;
	}
	
	/**
	 * get the direction vector
	 */
	
	public IVector getDirection (){
		return this.direction;
	}
	
	/**
	 * returns a clone of the actual DriverView
	 */
	
	public IDriverView clone (){
		return new DriverView(this.direction,this.position,this.angle,this.distance);
	}
	
	/**
	 * to string
	 */
	
	public String toString(){
		return new String("position: "+this.position.toString()+", direction: "+this.direction.toString()+", distance: "+this.distance);
	}
}
