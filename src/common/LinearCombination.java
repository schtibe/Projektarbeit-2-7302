package common;

/**
 * The result of a linear combination
 */
public class LinearCombination {
	private float mu;
	private float lambda;
	
	public LinearCombination (float m, float l){
		mu = m;
		lambda = l;
	}
	
	/**
	 * Get the mu value of the linear combination
	 * @return Mu
	 */
	public float getMu (){
		return mu;
	}
	
	/**
	 * Get the lambda value of the linear combination
	 * @return Lambda
	 */
	public float getLambda(){
		return lambda;
	}
}
