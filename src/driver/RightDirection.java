package driver;


/**
 * Right direction
 * 
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

	@Override
	public String toString (){
		return "right";
	}
}
