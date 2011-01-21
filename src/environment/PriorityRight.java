package environment;

import driver.IDirection;
import driver.LeftDirection;
import driver.RightDirection;

public class PriorityRight implements IPriority {

	private static int LEFT = -1;
	private static int STRAIGHT = 0;
	private static int RIGHT = 1;
	
	private int from,to,dir;
	
	/**
	 * forgive me the instanceof thing
	 */
	@Override
	public boolean hasPriority(IDirection dir, IDirection from, IDirection to) {
		this.dir = directionToInteger(dir);
		this.from = directionToInteger(from);
		this.to = directionToInteger(to);
		return assessPriority();
	}

	private int directionToInteger (IDirection d){
		if (d instanceof LeftDirection){
			return LEFT;
		}
		if (d instanceof RightDirection){
			return RIGHT;
		}
		return STRAIGHT;
	}

	@Override
	public void handleDir(IDirection dir) {
		System.out.println("handle dir called");
	}

	@Override
	public void handleFrom(IDirection dir) {
	}

	@Override
	public void handleTo(IDirection dir) {
	}
	
	private boolean assessPriority (){
		System.out.println("dir:"+dir+";from:"+from+";to:"+to);
		if (dir == STRAIGHT){
			if (from == RIGHT){
				return true;
			}
		}
		if (dir == LEFT){
			if (from == STRAIGHT && to != LEFT){
				return true;
			}
			if (from == RIGHT && to != RIGHT){
				return true;
			}
		}
		return false;
	}
}
