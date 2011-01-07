package driver;

import environment.IPriority;

public class RightDirection implements IDirection {

	@Override
	public boolean crossesMe(IDirection comingFrom, IDirection goingTo) {
		return false;
	}

	public boolean crossesMe(LeftDirection comingFrom, StraightDirection goingTo){
		return true;
	}
	
	public boolean crossesMe(StraightDirection comingFrom, LeftDirection goingTo){
		return true;
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

	@Override
	public RightDirection returnSelf() {
		return this;
	}
}
