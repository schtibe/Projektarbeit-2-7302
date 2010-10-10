package environment;

import java.util.List;

public class JunctionDecision implements IJunctionDecision {

	/**
	 * instance variables
	 */
	
	protected int id;
	protected List<ILane> lanes;
	protected IJunction junction;
	
	/**
	 * constructor for junction decision
	 * @param lanes
	 * @param id
	 * @param junction
	 */
	
	public JunctionDecision (List<ILane> lanes,int id,IJunction junction){
		this.lanes = lanes;
		this.id = id;
		this.junction = junction;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<ILane> getLanes() {
		return this.lanes;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public int getID() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public IJunction getJunction() {
		return this.junction;
	}

}
