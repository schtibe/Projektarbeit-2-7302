package driver;

public class StraightDirection implements IDirection {

	@Override
	public boolean crossesMe(IDirection comingFrom, IDirection goingTo) {
		return false;
	}
	
	public boolean crossesMe(StraightDirection comingFrom, LeftDirection goingTo){
		return true;
	}
	
	public boolean crossesMe (RightDirection comingFrom, IDirection goingTo){
		return true;
	}
	
	public boolean crossesMe (LeftDirection comingFrom, LeftDirection goingTo){
		return true;
	}
	
	public boolean crossesMe (LeftDirection comingFrom, StraightDirection goingTo){
		return true;
	}
}
