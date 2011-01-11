package driver;

import environment.IPriority;

/**
 * to whom it may concern: sorry or the instanceofs :(
 *
 */

public class LeftDirection implements IDirection {

	/**
	 * this is so damned dirty but still we had to do it
	 */
	@Override
	public boolean crossesMe(IDirection comingFrom, IDirection goingTo) {
		if (comingFrom instanceof LeftDirection){
			return comingFromLeft(goingTo);
		}else if (comingFrom instanceof RightDirection){
			return comingFromRight (goingTo);
		}else{
			return comingFromStraight (goingTo);
		}
	}
	
	/**
	 * used negative logic to minimize implementation effort
	 */
	
	private boolean comingFromLeft(IDirection goingTo){
		if (goingTo instanceof RightDirection){
			return false;
		}
		return true;
	}
	
	private boolean comingFromRight(IDirection goingTo){
		if (goingTo instanceof RightDirection){
			return false;
		}
		return true;
	}
	
	private boolean comingFromStraight(IDirection goingTo){
		if (goingTo instanceof LeftDirection){
			return false;
		}
		return true;
	}
	
	public String toString (){
		return "left";
	}

	@Override
	public void evaluateDir(IPriority priority) {
		priority.handleDir(this);
	}

	@Override
	public void evaluateFrom(IPriority priority) {
		priority.handleFrom(this);
	}

	@Override
	public void evaluateTo(IPriority priority) {
		priority.handleTo(this);
	}

}
