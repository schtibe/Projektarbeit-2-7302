package driver;

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

}
