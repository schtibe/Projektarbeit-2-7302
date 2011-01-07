package driver;

import environment.IPriority;

public class LeftDirection implements IDirection {

	@Override
	public boolean crossesMe(IDirection comingFrom, IDirection goingTo) {
		return true;
	}
	
	/**
	 * used negative logic to minimize implementation effort
	 */
	
	public boolean crossesMe(LeftDirection comingFrom, RightDirection goingTo){
		return false;
	}
	
	
	public boolean crossesMe(RightDirection comingFrom, RightDirection goingTo){
		return false;
	}
	
	public boolean crossesMe(StraightDirection comingFrom, LeftDirection goingTo){
		return false;
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
