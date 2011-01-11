package driver;

import environment.IPriority;

/**
 * to whom it may concern: sorry for the instanceofs 8(
 */

public class RightDirection implements IDirection {

	@Override
	public boolean crossesMe(IDirection comingFrom, IDirection goingTo) {
		if (comingFrom instanceof LeftDirection){
			if (goingTo instanceof StraightDirection){
				return true;
			}
		}
		if (comingFrom instanceof StraightDirection){
			if (goingTo instanceof LeftDirection){
				return true;
			}
		}
		return false;
	}

	public String toString (){
		return "right";
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
