package driver;

/**
 * Going straight
 * 
 * to whom it may concern: sorry for the instanceofs 8(
 */
public class StraightDirection implements IDirection {

	@Override
	public boolean crossesMe(IDirection comingFrom, IDirection goingTo) {
		if (comingFrom instanceof LeftDirection){
			return comingFromLeft (goingTo);
		}else if (comingFrom instanceof RightDirection){
			return true;
		}else{
			if (goingTo instanceof LeftDirection){
				return true;
			}
		}
		return false;
	}
	
	private boolean comingFromLeft(IDirection goingTo) {
		if (!(goingTo instanceof RightDirection)){
			return true;
		}
		return false;
	}
	
	@Override
	public String toString (){
		return "straight";
	}
}
