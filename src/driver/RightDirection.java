package driver;

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
}
