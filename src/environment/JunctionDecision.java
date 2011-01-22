package environment;

import java.util.List;

import driver.IDirection;

public class JunctionDecision implements IJunctionDecision {

	/**
	 * instance variables
	 */
	
	protected int id;
	protected List<ILane> lanes;
	protected IJunction junction;
	protected IDirection direction;
	
	/**
	 * constructor for junction decision
	 * @param lanes
	 * @param id
	 * @param junction
	 */
	
	public JunctionDecision (List<ILane> lanes,int id,IJunction junction,IDirection direction){
		this.lanes = lanes;
		this.id = id;
		this.junction = junction;
		this.direction = direction;
	}
	
	
	@Override
	public List<ILane> getLanes() {
		return this.lanes;
	}


	@Override
	public int getID() {
		return this.id;
	}

	
	@Override
	public IJunction getJunction() {
		return this.junction;
	}

	@Override
	public IDirection getDirection() {
		return this.direction;
	}

}
