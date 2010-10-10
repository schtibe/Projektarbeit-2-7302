package driver;

import common.IVector;

public interface IDriverView {

	public abstract void setAngle(float value);

	public abstract void setDistance(float value);

	public abstract void setDirection(IVector value);

	public abstract void setPosition(IVector value);

	public abstract float getAngle();

	public abstract float getDistance();

	public abstract IVector getPosition();

	public abstract IVector getDirection();
	
	public abstract IDriverView clone();

}