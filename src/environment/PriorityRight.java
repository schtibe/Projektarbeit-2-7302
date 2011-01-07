package environment;

import driver.IDirection;
import driver.LeftDirection;
import driver.RightDirection;
import driver.StraightDirection;

public class PriorityRight implements IPriority {

	private static int LEFT = -1;
	private static int STRAIGHT = 0;
	private static int RIGHT = 1;
	
	private int from,to,dir;
	
	@Override
	public boolean hasPriority(IDirection dir, IDirection from, IDirection to) {
		dir.evaluateDir(this);
		from.evaluateFrom(this);
		to.evaluateTo(this);
		return assessPriority();
	}
	
	public void handleDir (LeftDirection dir){
		this.dir = LEFT;
	}
	
	public void handleDir (RightDirection dir){
		this.dir = RIGHT;
	}
	
	public void handleDir (StraightDirection dir){
		this.dir = STRAIGHT;
	}
	
	public void handleFrom (LeftDirection dir){
		from = LEFT;
	}
	
	public void handleFrom (RightDirection dir){
		from = RIGHT;
	}
	
	public void handleFrom (StraightDirection dir){
		from = STRAIGHT;
	}
	
	public void handleTo (LeftDirection dir){
		to = LEFT;
	}
	
	public void handleTo (RightDirection dir){
		to = RIGHT;
	}
	
	public void handleTo (StraightDirection dir){
		to = STRAIGHT;
	}

	@Override
	public void handleDir(IDirection dir) {
	}

	@Override
	public void handleFrom(IDirection dir) {
	}

	@Override
	public void handleTo(IDirection dir) {
	}
	
	private boolean assessPriority (){
		if (dir == LEFT){
			if (from == STRAIGHT){
				if (to != LEFT){
					return false;
				}
			}else if (from == RIGHT){
				if (to != RIGHT){
					return false;
				}
			}
		}
		if (dir == STRAIGHT){
			if (from == RIGHT){
				return false;
			}
		}
		return true;
	}
}
